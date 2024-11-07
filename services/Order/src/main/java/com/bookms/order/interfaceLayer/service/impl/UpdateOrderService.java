package com.bookms.order.interfaceLayer.service.impl;

import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.usecase.impl.UpdateOrderUseCase;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.core.domain.Entity.Status;
import com.bookms.order.core.domain.Exception.OrderNotFoundException;
import com.bookms.order.core.domain.Exception.OrderOutOfCancelTime;
import com.bookms.order.core.domain.State.StaticVar;
import com.bookms.order.infrastructure.jpaRepository.OrderJpaRepository;
import com.bookms.order.interfaceLayer.service.IUpdateOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.bookms.order.core.domain.State.StaticVar.*;

@Service
@RequiredArgsConstructor
public class UpdateOrderService implements IUpdateOrderService {
    private final UpdateOrderUseCase updateOrderUseCase;
    private final OrderJpaRepository orderJpaRepository;
    private final KafkaTemplate<String,OrdersModel> kafkaTemplate;
    private final ModelMapper modelMapper;

    @Override
    public void updateStatusAfterShipped(Integer shipmentId) {
        Orders order = orderJpaRepository.findOneByShipmentId(shipmentId).orElseThrow(
                () -> new OrderNotFoundException(String.format("Order not found %s",shipmentId))
        );

        order.setStatus(Status.COMPLETED);
        orderJpaRepository.save(order);
    }

    @Override
    public Orders cancelOrderById(int id) {
        Orders orders = orderJpaRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException(String.format("Order not found %s",id))
        );
        orders.setStatus(Status.CANCELLED);
        OrdersModel model = modelMapper.map(orders, OrdersModel.class);
        kafkaTemplate.send("cancel-order",model);
        return orderJpaRepository.save(orders);
    }

    @Override
    public Orders cancelOrderByOrderNumber(Long orderNumber) {
        Orders orders = orderJpaRepository.findByOrderNumber(orderNumber).orElseThrow(
                () -> new OrderNotFoundException(String.format("Order not found %s",orderNumber))
        );
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime orderCreatedDate = orders.getCreatedDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        Duration duration = Duration.between(orderCreatedDate, now);
        if(duration.toHours() > LIMIT_HOURS_TO_CANCELLED){
            throw new OrderOutOfCancelTime("your order out of 12 hours to cancel");
        }

        orders.setStatus(Status.CANCELLED);
        OrdersModel model = modelMapper.map(orders, OrdersModel.class);
        orders.setShipmentId(null);
        model.setInventoryId(1);
        kafkaTemplate.send("cancel-order",model);
        return orderJpaRepository.save(orders);
    }
}
