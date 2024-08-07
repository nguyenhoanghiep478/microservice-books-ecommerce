package com.bookms.order.interfaceLayer.service;

import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.model.PaymentModel;
import com.bookms.order.interfaceLayer.DTO.OrderDTO;

import java.util.List;

public interface IOrderService {
    List<OrderDTO> findAll();

    OrderDTO findById(int id);

    OrderDTO createOrder(OrdersModel request);

   OrderDTO findByOrderNumber(Long orderNumber);

    PaymentModel prePay(OrderDTO request);

    OrdersModel afterPay(Long orderNumber);

    void completeOrder(Long key);
}
