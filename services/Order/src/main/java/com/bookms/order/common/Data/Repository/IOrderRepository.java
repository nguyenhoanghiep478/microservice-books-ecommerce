package com.bookms.order.common.Data.Repository;

import com.bookms.order.common.Data.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Orders, Integer> {
    Optional<Orders> findById(int id);
    Optional<Orders> findOneByOrderNumber(Long orderNumber);
}
