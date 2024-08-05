package com.bookms.order.infrastructure.repository;

import com.bookms.order.core.domain.Entity.OrderType;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.core.domain.Entity.Status;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import com.bookms.order.infrastructure.jpaRepository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class OrderRepository implements IOrderRepository {
    private final OrderJpaRepository jpaRepository;
    private final OrderJpaRepository orderJpaRepository;


    @Override
    public Optional<Orders> findByOrderNumberAndCustomerIdAndOrderType(Long orderNumber, Integer customerId, OrderType orderType) {
        return Optional.empty();
    }

    @Override
    public Optional<Orders> findByOrderNumberAndCustomerId(Long orderNumber, Integer customerId) {
        return Optional.empty();
    }

    @Override
    public Optional<Orders> findByOrderNumber(Long orderNumber) {

        return orderJpaRepository.findByOrderNumber(orderNumber);
    }

    @Override
    public Optional<Orders> findByIdAndOrderNumberAndCustomerIdAndOrderType(Integer id, Long orderNumber, Integer customerId, OrderType orderType) {
        return Optional.empty();
    }

    @Override
    public Optional<Orders> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Orders> findAllByCustomerIdAndOrderTypeAndStatusAndTotalPriceAndPaymentMethod(Integer customerId, OrderType orderType, Status status, BigDecimal totalPrice, String paymentMethod) {
        return List.of();
    }

    @Override
    public List<Orders> findAllByCustomerIdAndOrderTypeAndStatus(Integer customerId, OrderType orderType, Status status) {
        return List.of();
    }

    @Override
    public List<Orders> findAllByCustomerIdAndOrderType(Integer customerId, OrderType orderType) {
        return List.of();
    }

    @Override
    public List<Orders> findAllByCustomerId(Integer customerId) {
        return List.of();
    }

    @Override
    public List<Orders> findAllByOrderType(OrderType orderType) {
        return List.of();
    }

    @Override
    public List<Orders> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Orders save(Orders orders) {
        return jpaRepository.save(orders);
    }
}
