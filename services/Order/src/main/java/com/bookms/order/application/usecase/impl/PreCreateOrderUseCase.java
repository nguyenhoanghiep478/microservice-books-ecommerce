package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.BaseUseCase;
import com.bookms.order.application.model.BookModel;
import com.bookms.order.application.model.OrderItemModel;
import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.servicegateway.IBookServiceGateway;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.core.domain.Exception.*;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class PreCreateOrderUseCase implements BaseUseCase<OrdersModel, OrdersModel>{
    private final IOrderRepository orderRepository;
    private final IBookServiceGateway bookServiceGateway;

    @Override
    public OrdersModel execute(OrdersModel orders) {
        orders.setOrderItems(toSet(orders.getOrderItems()));
        if(orders.getOrderNumber() != null){
            Optional<Orders> order = orderRepository.findByOrderNumber(orders.getOrderNumber());
            if(order.isPresent()){
                throw new OrderExistException("order number already exist");
            }
        }

        List<BookModel> bookModels = getBookModels(orders);

        BigDecimal totalPrice =  validOrder(orders,bookModels);
        orders.setTotalPrice(totalPrice);


        for(int i = 0 ; i < bookModels.size() ; i++){
            orders.getOrderItems().get(i).setName(bookModels.get(i).getName());
        }

        orders.setBookModels(bookModels);
        return orders;
    }



    private BigDecimal validOrder(OrdersModel order, List<BookModel> bookModels){
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(int i = 0 ; i<order.getOrderItems().size() ; i++){
            BigDecimal price = bookModels.get(i).getPrice();
            if(!price.equals(bookModels.get(i).getPrice()) ){
                throw new PriceNotTheSameException(String.format("price for item %s not the same with store",bookModels.get(i).getName()));
            }
            totalPrice = totalPrice.add(price.multiply(BigDecimal.valueOf(order.getOrderItems().get(i).getTotalQuantity())));

        }
        if(order.getTotalPrice()!=null){
            totalPrice = totalPrice.stripTrailingZeros();
            order.setTotalPrice(order.getTotalPrice().stripTrailingZeros());
            if(!totalPrice.equals(order.getTotalPrice())){
                throw new TotalPriceNotTheSameException(String.format("total price for order %s not correct ",order.getOrderNumber()));
            }
        }
        return totalPrice;
    }

    private List<BookModel> getBookModels(OrdersModel orders){
        Set<Integer> booksId = orders.getOrderItems().stream().map(OrderItemModel::getBookId).collect(Collectors.toSet());

        List<BookModel> result = bookServiceGateway.findAllBookWithListId(booksId);
        if(result.isEmpty()){
            throw new BookNotFoundException(String.format("books in order not found"));
        }
        return result;
    }

    private List<OrderItemModel> toSet(List<OrderItemModel> orderItems){
        List<OrderItemModel> result = new ArrayList<>();
        Hashtable<Integer,Integer> setBookId = new Hashtable<>();
        for(int i = 0 ; i < orderItems.size() ; i++){
            if(!setBookId.containsKey(orderItems.get(i).getBookId())){
                setBookId.put(orderItems.get(i).getBookId(),i);
                result.add(orderItems.get(i));
            }else{
                int indexDuplicate = setBookId.get(orderItems.get(i).getBookId());
                int currentQuantity = result.get(indexDuplicate).getTotalQuantity();
                int newQuantity = orderItems.get(i).getTotalQuantity() + currentQuantity;
                result.get(setBookId.get(orderItems.get(indexDuplicate).getBookId())).setTotalQuantity(newQuantity);
            }
        }
        return result;
    }
}
