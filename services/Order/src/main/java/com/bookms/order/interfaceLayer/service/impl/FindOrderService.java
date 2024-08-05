package com.bookms.order.interfaceLayer.service.impl;

import com.bookms.order.application.model.OrderSearchCriteria;
import com.bookms.order.application.usecase.FindOrderUseCase;
import com.bookms.order.application.usecase.FindOrdersUseCase;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.interfaceLayer.service.IFindOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindOrderService implements IFindOrderService {
    private final FindOrderUseCase findOrderUseCase;
    private final FindOrdersUseCase findOrdersUseCase;


    @Override
    public Orders findById(int id) {
        return findOrderUseCase.execute(OrderSearchCriteria
                .builder()
                .id(id)
                .build());
    }

    @Override
    public Orders findByOrderNumber(Long orderNumber) {
        return findOrderUseCase.execute(OrderSearchCriteria.builder()
                .orderNumber(orderNumber)
                .build());
    }

    @Override
    public List<Orders> findAll() {
        return findOrdersUseCase.execute(null);
    }
}
