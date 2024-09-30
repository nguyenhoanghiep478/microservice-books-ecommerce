package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.BaseUseCase;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.core.domain.Entity.Status;
import com.bookms.order.core.domain.Exception.PriceNotTheSameException;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class CreateBuyOrderUseCase implements BaseUseCase<Orders,Orders> {
    private IOrderRepository orderRepository;

    @Transactional(rollbackFor = Exception.class)
    public Orders execute(Orders orders) {
        orders.setStatus(Status.COMPLETED);
        Random random = new Random();
        orders.setOrderNumber(Math.abs(random.nextLong()));
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(int i = 0 ; i<orders.getOrderItems().size() ; i++){
            BigDecimal price = orders.getOrderItems().get(i).getPrice().stripTrailingZeros();
            BigDecimal orderPrice = orders.getOrderItems().get(i).getPrice().stripTrailingZeros();
            totalPrice = totalPrice.add(price.multiply(BigDecimal.valueOf(orders.getOrderItems().get(i).getTotalQuantity())));

        }
        orders.setTotalPrice(totalPrice);
        return orderRepository.save(orders);
    }
}
