package com.bookms.order.interfaceLayer.service;

import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.interfaceLayer.DTO.OrderDTO;

public interface ICreateOrderService {
    OrdersModel createOrder(OrderDTO request);
}
