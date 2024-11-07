package com.bookms.order.interfaceLayer.service;

import com.bookms.order.core.domain.Entity.Orders;

public interface IUpdateOrderService {
    void updateStatusAfterShipped(Integer id);

    Orders cancelOrderById(int id);

    Orders cancelOrderByOrderNumber(Long orderNumber);
}
