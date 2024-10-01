package com.bookms.order.interfaceLayer.service.impl;

import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.model.PaymentModel;
import com.bookms.order.application.usecase.impl.CreateOrderUseCase;
import com.bookms.order.application.usecase.impl.PreCreateOrderUseCase;
import com.bookms.order.core.domain.Entity.OrderType;
import com.bookms.order.core.domain.Entity.Status;
import com.bookms.order.core.domain.Exception.InvalidToken;
import com.bookms.order.core.domain.State.PaymentMethod;
import com.bookms.order.infrastructure.serviceGateway.impl.MarketingServiceGateway;
import com.bookms.order.interfaceLayer.DTO.OrderDTO;
import com.bookms.order.interfaceLayer.DTO.ResponseOrderCreated;
import com.bookms.order.interfaceLayer.DTO.ResponsePayment;
import com.bookms.order.interfaceLayer.service.ICreateOrderService;
import com.bookms.order.interfaceLayer.service.IUpdateOrderService;
import com.bookms.order.interfaceLayer.service.redis.OrderRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.bookms.order.core.domain.Entity.Status.*;
import static com.bookms.order.core.domain.State.Concurency.USD;
import static com.bookms.order.core.domain.State.StaticPayment.PAYPAL;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateOrderService implements ICreateOrderService {
    private final CreateOrderUseCase createOrderUseCase;
    private final OrderRedisService orderRedisService;
    private final IUpdateOrderService updateOrderService;
    private final PreCreateOrderUseCase preCreateOrderUseCase;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, OrdersModel> kafkaTemplate;
    private final MarketingServiceGateway marketingServiceGateway;

    @Override
    @KafkaListener(id = "stock-in-consumer",topics = "stock-in")
    public OrdersModel createOrder(OrdersModel request) {
        Random random = new Random();
        request.setShipmentId( random.nextInt((100000- 200)+1)+200);

        OrdersModel ordersModel = createOrderUseCase.execute(request);

        ordersModel.setRecipient(request.getRecipient());
        ordersModel.setInventoryId(1);
        if(request.getOrderType().equals(OrderType.SELL) && request.getStatus().equals(SHIPPING)){
            kafkaTemplate.send("order-created", ordersModel);
        }
        return ordersModel;
    }
    @KafkaListener(id = "consumer-created-order-response",topics = "order-created-response")
    public void preCreate(ResponseOrderCreated orderCreated) {
        log.info("{} message: {}", orderCreated.getServiceName(), orderCreated.getMessage());

    }

    @Override
    public PaymentModel prePayment(OrderDTO request) {
        OrdersModel ordersModel = null;
        if(request.getToken() == null || request.getToken().equals("0") ){
             ordersModel =  preCreateOrderUseCase.execute(modelMapper.map(request,OrdersModel.class));
             orderRedisService.saveOrder(ordersModel);
             kafkaTemplate.send("pre-create-order",ordersModel);
             return null;
        }

        if(!marketingServiceGateway.validateToken(Long.valueOf(request.getToken()))){
            throw new InvalidToken("invalid token");
        }

        ordersModel = orderRedisService.getOrder(request.getOrderNumber());
        createOrderUseCase.execute(ordersModel);
        return getPaymentModel(ordersModel);
    }

    @Override
    public OrdersModel afterPayment(Long orderNumber) {
        return orderRedisService.getOrder(orderNumber);
    }

    @Override
    public void completeOrder(Long key) {
        orderRedisService.removeOrder(key);
    }

    @KafkaListener(id = "payment-response",topics = "payment-response")
    public void handleResponse(ResponsePayment responsePayment) {
        OrdersModel orderWasPaid= afterPayment(responsePayment.getOrderNumber());
        orderWasPaid.setPaymentMethod(responsePayment.getPaymentMethod());
        orderWasPaid.setPaymentId(responsePayment.getPaymentId());
        if(responsePayment.getStatus().equals(COMPLETED)){
            orderWasPaid.setStatus(SHIPPING);
        }else{
            orderWasPaid.setStatus(responsePayment.getStatus());
        }

        createOrder(orderWasPaid);
    }

    @KafkaListener(id = "shipment-response",topics = "shipped")
    public void updateOrderStatus(Integer id){
        updateOrderService.updateStatusAfterShipped(id);
    }

    @Override
    public OrdersModel handleOrderWasPaid(ResponsePayment responsePayment) {
        OrdersModel orderWasPaid= afterPayment(responsePayment.getOrderNumber());
        orderWasPaid.setPaymentMethod(responsePayment.getPaymentMethod());
        orderWasPaid.setPaymentId(responsePayment.getPaymentId());
        orderWasPaid.setStatus(responsePayment.getStatus());
        return orderWasPaid;
    }

    @Override
    public OrdersModel handleCodOrder(OrdersModel model) {
        OrdersModel preCreateOrder = preCreateOrderUseCase.execute(model);
        preCreateOrder.setPaymentMethod(PaymentMethod.COD.getValue());
        preCreateOrder.setStatus(PROCESSING);
        return createOrder(preCreateOrder);
    }


    private PaymentModel getPaymentModel(OrdersModel ordersModel) {
        return PaymentModel.builder()
                .orderNumber(ordersModel.getOrderNumber())
                .customerId(ordersModel.getCustomerId())
                .total(ordersModel.getTotalPrice().doubleValue())
                .currency(USD.getConcurency())
                .description(PAYPAL.description)
                .intent(PAYPAL.intent)
                .method(ordersModel.getPaymentMethod())
                .build();
    }
}
