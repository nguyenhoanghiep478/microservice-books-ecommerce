package com.bookms.order.common.Service;

import com.bookms.order.common.Data.DTO.OrderDTO;

import java.util.List;

public interface IOrderService {
    List<OrderDTO> findAll();

    OrderDTO findById(int id);

    OrderDTO createOrder(OrderDTO request);

}
