package com.bookms.order.interfaceLayer.service;

import com.bookms.order.interfaceLayer.DTO.OrderDTO;

import java.util.List;

public interface IOrderService {
    List<OrderDTO> findAll();

    OrderDTO findById(int id);

    OrderDTO createOrder(OrderDTO request);

   OrderDTO findByOrderNumber(Long orderNumber);

}
