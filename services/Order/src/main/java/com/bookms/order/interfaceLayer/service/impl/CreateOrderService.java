package com.bookms.order.interfaceLayer.service.impl;

import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.usecase.CreateOrderUseCase;
import com.bookms.order.interfaceLayer.DTO.OrderDTO;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.interfaceLayer.service.ICreateOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.bookms.order.web.mapper.OrderMapper.toEntity;

@Service
@RequiredArgsConstructor
public class CreateOrderService implements ICreateOrderService {
    private final CreateOrderUseCase createOrderUseCase;

    @Override
    public OrdersModel createOrder(OrderDTO request) {
        Orders order = toEntity(request);
        return createOrderUseCase.execute(order);
    }
}
