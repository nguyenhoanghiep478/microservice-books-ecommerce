package com.bookms.order.interfaceLayer.service.impl;

import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.model.PaymentModel;
import com.bookms.order.application.usecase.CreateOrderUseCase;
import com.bookms.order.application.usecase.PreCreateOrderUseCase;
import com.bookms.order.interfaceLayer.DTO.OrderDTO;
import com.bookms.order.interfaceLayer.service.ICreateOrderService;
import com.bookms.order.interfaceLayer.service.redis.OrderRedisService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrderService implements ICreateOrderService {
    private final CreateOrderUseCase createOrderUseCase;
    private final OrderRedisService orderRedisService;
    private final PreCreateOrderUseCase preCreateOrderUseCase;
    private final ModelMapper modelMapper;

    @Override
    public OrdersModel createOrder(OrdersModel request) {
        return createOrderUseCase.execute(request);
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
