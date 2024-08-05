package com.bookms.order.interfaceLayer.service;

import com.bookms.order.core.domain.Entity.Orders;

import java.util.List;

public interface IFindOrderService {
    Orders findById(int id);
    Orders findByOrderNumber(Long orderNumber);
    List<Orders> findAll();
}
