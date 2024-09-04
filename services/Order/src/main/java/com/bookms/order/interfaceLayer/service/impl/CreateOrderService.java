package com.bookms.order.interfaceLayer.service.impl;

import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.model.PaymentModel;
import com.bookms.order.application.usecase.impl.CreateOrderUseCase;
import com.bookms.order.application.usecase.impl.PreCreateOrderUseCase;
import com.bookms.order.infrastructure.serviceGateway.MarketingServiceGateway;
import com.bookms.order.interfaceLayer.DTO.OrderDTO;
import com.bookms.order.interfaceLayer.DTO.ResponseOrderCreated;
import com.bookms.order.interfaceLayer.DTO.ResponsePayment;
import com.bookms.order.interfaceLayer.service.ICreateOrderService;
import com.bookms.order.interfaceLayer.service.redis.OrderRedisService;
import com.booksms.marketing.core.domain.exception.InvalidToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.bookms.order.core.domain.State.Concurency.USD;
import static com.bookms.order.core.domain.State.StaticPayment.PAYPAL;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateOrderService implements ICreateOrderService {
    private final CreateOrderUseCase createOrderUseCase;
    private final OrderRedisService orderRedisService;
    private final PreCreateOrderUseCase preCreateOrderUseCase;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, OrdersModel> kafkaTemplate;
    private final MarketingServiceGateway marketingServiceGateway;

    @Override
    public OrdersModel createOrder(OrdersModel request) {
        OrdersModel ordersModel = createOrderUseCase.execute(request);
        ordersModel.setRecipient(request.getRecipient());
        kafkaTemplate.send("order-created", ordersModel);
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
             kafkaTemplate.send("pre-create-order",ordersModel);
             orderRedisService.saveOrder(ordersModel);
             return null;
        }
        if(!marketingServiceGateway.validateToken(Long.valueOf(request.getToken()))){
            throw new InvalidToken("invalid token");
        }

        ordersModel = orderRedisService.getOrder(request.getOrderNumber());
        createOrderUseCase.execute(ordersModel);
        return PaymentModel.builder()
                .orderNumber(ordersModel.getOrderNumber())
                .customerId(ordersModel.getCustomerId())
                .total(ordersModel.getTotalPrice().doubleValue())
                .currency(USD.getConcurency())
                .description(PAYPAL.description)
                .intent(PAYPAL.intent)
                .method(PAYPAL.method)
                .build();
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
        orderWasPaid.setStatus(responsePayment.getStatus());

        createOrder(orderWasPaid);
    }

    @Override
    public OrdersModel handleOrderWasPaid(ResponsePayment responsePayment) {
        OrdersModel orderWasPaid= afterPayment(responsePayment.getOrderNumber());
        orderWasPaid.setPaymentMethod(responsePayment.getPaymentMethod());
        orderWasPaid.setPaymentId(responsePayment.getPaymentId());
        orderWasPaid.setStatus(responsePayment.getStatus());

        return orderWasPaid;
    }

}
