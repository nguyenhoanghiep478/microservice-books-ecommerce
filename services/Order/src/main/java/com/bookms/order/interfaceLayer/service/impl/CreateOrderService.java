package com.bookms.order.interfaceLayer.service.impl;

import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.model.PaymentModel;
import com.bookms.order.application.usecase.CreateOrderUseCase;
import com.bookms.order.application.usecase.PreCreateOrderUseCase;
import com.bookms.order.interfaceLayer.DTO.OrderDTO;
import com.bookms.order.interfaceLayer.DTO.ResponseOrderCreated;
import com.bookms.order.interfaceLayer.service.ICreateOrderService;
import com.bookms.order.interfaceLayer.service.redis.OrderRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateOrderService implements ICreateOrderService {
    private final CreateOrderUseCase createOrderUseCase;
    private final OrderRedisService orderRedisService;
    private final PreCreateOrderUseCase preCreateOrderUseCase;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, OrdersModel> kafkaTemplate;
    @Override
    public OrdersModel createOrder(OrdersModel request) {
        OrdersModel ordersModel = createOrderUseCase.execute(request);
        ordersModel.setRecipient("nguyenuoanghiep4790@gmail.com");
        kafkaTemplate.send("order-created", ordersModel);
        return ordersModel;
    }
    @KafkaListener(id = "consumer-created-order-response",topics = "order-created-response")
    public void preCreate(ResponseOrderCreated orderCreated) {
        log.info("{} message: {}", orderCreated.getServiceName(), orderCreated.getMessage());

    }

    @Override
    public PaymentModel prePayment(OrderDTO request) {
        OrdersModel ordersModel =  preCreateOrderUseCase.execute(modelMapper.map(request,OrdersModel.class));
        orderRedisService.saveOrder(ordersModel);
        return PaymentModel.builder()
                .orderNumber(ordersModel.getOrderNumber())
                .customerId(ordersModel.getCustomerId())
                .total(ordersModel.getTotalPrice().doubleValue())
                .currency("USD")
                .description("payment paypal")
                .intent("sale")
                .method("paypal")
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


}
