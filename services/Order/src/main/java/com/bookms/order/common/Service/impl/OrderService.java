package com.bookms.order.common.Service.impl;

import com.bookms.order.common.Data.DTO.BookDTO;
import com.bookms.order.common.Data.DTO.OrderDTO;
import com.bookms.order.common.Data.DTO.OrderItemDTO;
import com.bookms.order.common.Data.DTO.Request.UpdateQuantityDTO;
import com.bookms.order.common.Data.DTO.ResponseDTO;
import com.bookms.order.common.Data.Entity.Orders;
import com.bookms.order.common.Data.Entity.OrderItems;
import com.bookms.order.common.Data.Entity.OrderType;
import com.bookms.order.common.Data.Repository.IOrderRepository;
import com.bookms.order.common.Service.GenericMapper;
import com.bookms.order.common.Service.IOrderService;
import com.bookms.order.start.Config.Exception.BookNotFoundException;
import com.bookms.order.start.Config.Exception.InSufficientQuantityException;
import com.bookms.order.start.Config.Exception.OrderExistException;
import com.bookms.order.start.Config.Exception.OrderNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final IOrderRepository orderRepository;
    private final GenericMapper<Orders, OrderDTO, OrderDTO> mapper;
    private final GenericMapper<OrderItems, OrderItemDTO, OrderItemDTO> orderItemMapper;
    private final RestTemplate restTemplate;
    private String bookUrl = "http://localhost:5555/api/v1/book/";

    @Override
    public List<OrderDTO> findAll() {
        List<Orders> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new InternalServerErrorException("Some thing wrong,please try again");
        }
        List<OrderDTO> ordersDTO = mapper.toListResponse(orders, OrderDTO.class);
        if (ordersDTO.isEmpty()) {
            throw new InternalServerErrorException("Some thing wrong,please try again");
        }
        return ordersDTO;
    }

    @Override
    public OrderDTO findById(int id) {
        Optional<Orders> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new OrderNotFoundException(String.format("Orders with id %s not found", id));
        }
        OrderDTO orderDTO = mapper.toResponse(order.get(), OrderDTO.class);
        if (orderDTO == null) {
            throw new InternalServerErrorException("Some thing wrong,please try again");
        }
        return orderDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO createOrder(OrderDTO request) {
        //check orders exist
        OrderDTO orderDTO = findByOrderNumber(request.getOrderNumber());
        if (orderDTO != null) {
            throw new OrderExistException(String.format("This order with order number %d already exists", request.getOrderNumber()));
        }
        try {
            //getBooks
            List<BookDTO> booksDTO = getBooksDTO(request);
            for(BookDTO bookDTO : booksDTO) {
                //ValidOrder
                validateOrders(bookDTO, request);
            }
            //createOrders
            Orders orders = toOrders(request, booksDTO);
            //save order
            Orders result = orderRepository.save(orders);
            //convertDTO
            OrderDTO resultDTO = toDTO(result);
            if (resultDTO == null) {
                throw new InternalServerErrorException("Something wrong,please try again");
            }
            //update quantity
            updateQuantity(request);
            return resultDTO;

        } catch (HttpClientErrorException.NotFound e) {
            throw new BookNotFoundException(String.format("Book with id %s not found", request.getOrderItems().get(0).getBookId()));
        } catch (InSufficientQuantityException e) {
            throw new InSufficientQuantityException(e.getMessage());
        }
    }

    public OrderDTO findByOrderNumber(Long orderNumber) {
        Optional<Orders> order = orderRepository.findOneByOrderNumber(orderNumber);
        if (order.isEmpty()) {
            return null;
        }
        OrderDTO orderDTO = mapper.toResponse(order.get(), OrderDTO.class);
        if (orderDTO == null) {
            throw new InternalServerErrorException("Some thing wrong,please try again");
        }
        return orderDTO;
    }

    public Orders toEntity(OrderDTO orderDTO) {
        return Orders.builder()
                .orderNumber(orderDTO.getOrderNumber())
                .paymentMethod(orderDTO.getPaymentMethod())
                .orderType(orderDTO.getOrderType())
                .status(orderDTO.getStatus())
                .totalPrice(orderDTO.getTotalPrice())
                .orderItems(orderDTO.getOrderItems().stream().map(item -> OrderItems
                                .builder()
                                .build())
                        .toList())
                .build();
    }

    public OrderDTO toDTO(Orders orders) {
        return OrderDTO.builder()
                .orderNumber(orders.getOrderNumber())
                .paymentMethod(orders.getPaymentMethod())
                .status(orders.getStatus())
                .orderType(orders.getOrderType())
                .totalPrice(orders.getTotalPrice())
                .orderItems(orders.getOrderItems().stream().map(item -> OrderItemDTO.builder()
                                .price(item.getPrice())
                                .bookId(item.getBookId())
                                .totalQuantity(item.getTotalQuantity())
                                .orderId(item.getOrders().getId())
                                .build())
                        .toList()
                )
                .build();
    }

    private BookDTO getBookDTO(Integer bookId) {
        final String handleAction = "get-by-id/";
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseEntity<ResponseDTO> responseEntity = this.restTemplate.getForEntity(bookUrl + handleAction + bookId, ResponseDTO.class);
        ResponseDTO responseDTO= responseEntity.getBody();
        if (responseDTO == null) {
            throw new BookNotFoundException(String.format("Book with id %s not found", bookId));
        }
        return objectMapper.convertValue(responseDTO.getResult(), BookDTO.class);
    }

    private List<BookDTO> getBooksDTO(OrderDTO request) {
        List<BookDTO> bookDTOs = new ArrayList<>();
       for (OrderItemDTO item : request.getOrderItems()) {
           BookDTO bookDTO = getBookDTO(item.getBookId());
           bookDTOs.add(bookDTO);
       }
        return bookDTOs;
    }

    private void updateQuantity(OrderDTO request) {
        String updateQuantityURL = "/update-quantity";
        for (OrderItemDTO item : request.getOrderItems()) {
            UpdateQuantityDTO updateQuantityDTO = UpdateQuantityDTO.builder()
                    .quantity(item.getTotalQuantity())
                    .type(request.getOrderType())
                    .build();
            ResponseEntity<ResponseDTO> responseEntity = this.restTemplate.postForEntity(bookUrl + item.getBookId() + updateQuantityURL, updateQuantityDTO, ResponseDTO.class);
            if (responseEntity.getBody() == null) {
                throw new InternalServerErrorException("something wrong,please try again");
            }
        }
    }

    private Orders toOrders(OrderDTO request, List<BookDTO> booksDTO) {
        Orders orders = toEntity(request);
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        BigDecimal price;
        if (orders == null) {
            throw new InternalServerErrorException("Some thing wrong,please try again");
        }
        int index = 0;
        BookDTO bookDTO = null;
        OrderItemDTO orderItemDTO = null;
        for (OrderItems orderItems : orders.getOrderItems()) {
            bookDTO = booksDTO.get(index);
            orderItemDTO = request.getOrderItems().get(index++);
            orderItems.setOrders(orders);
            orderItems.setPrice(bookDTO.getPrice());
            orderItems.setTotalQuantity(orderItemDTO.getTotalQuantity());
            orderItems.setBookId(bookDTO.getId());
            price = bookDTO.getPrice();
            totalPrice = totalPrice.add(price.multiply(BigDecimal.valueOf(orderItemDTO.getTotalQuantity())));
        }
        orders.setTotalPrice(totalPrice);
        return orders;
    }

    private void validateOrders(BookDTO bookDTO, OrderDTO request) {
        if (bookDTO.getAvailableQuantity() < request.getOrderItems().get(0).getTotalQuantity()) {
            throw new InSufficientQuantityException(String.format("Not enough book with name %s for orders",bookDTO.getName()));
        }
    }
}
