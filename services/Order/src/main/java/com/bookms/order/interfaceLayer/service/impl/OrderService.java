package com.bookms.order.interfaceLayer.service.impl;

import com.bookms.order.interfaceLayer.DTO.OrderDTO;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.interfaceLayer.service.ICreateOrderService;
import com.bookms.order.interfaceLayer.service.IFindOrderService;
import com.bookms.order.interfaceLayer.service.IOrderService;
import com.bookms.order.core.domain.Exception.BookNotFoundException;
import com.bookms.order.core.domain.Exception.InSufficientQuantityException;
import com.bookms.order.core.domain.Exception.OrderExistException;
import com.bookms.order.core.domain.Exception.OrderNotFoundException;
import com.bookms.order.web.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final ICreateOrderService createOrderService;
    private final IFindOrderService findOrderService;
    private final ModelMapper modelMapper;

    @Override
    public List<OrderDTO> findAll() {
        List<OrderDTO> result = new ArrayList<>();
        List<Orders> orders = findOrderService.findAll();
        if(orders.isEmpty()) {
           throw new OrderNotFoundException("cannot find any orders");
        }
        for (Orders order : orders) {
            result.add(modelMapper.map(order, OrderDTO.class));
        }
       return result;
    }

    @Override
    public OrderDTO findById(int id) {
        try {
            return modelMapper.map(findOrderService.findById(id),OrderDTO.class);
        }catch (OrderNotFoundException e){
            throw new OrderNotFoundException(e.getMessage());
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO createOrder(OrderDTO request) {
        try {
           return OrderMapper.toDTO(createOrderService.createOrder(request));
        } catch (HttpClientErrorException.NotFound e) {
            throw new BookNotFoundException(String.format("Book with id %s not found", request.getOrderItems().get(0).getBookId()));
        } catch (InSufficientQuantityException e) {
            throw new InSufficientQuantityException(e.getMessage());
        } catch (OrderExistException e) {
            throw new OrderExistException(e.getMessage());
        }
    }
    @Override
    public OrderDTO findByOrderNumber(Long orderNumber) {
        return modelMapper.map(findOrderService.findByOrderNumber(orderNumber),OrderDTO.class);
    }



}
