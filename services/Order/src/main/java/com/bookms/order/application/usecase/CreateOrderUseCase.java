package com.bookms.order.application.usecase;

import com.bookms.order.application.BaseUseCase;
import com.bookms.order.application.model.BookModel;
import com.bookms.order.application.model.OrderItemModel;
import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.model.UpdateQuantityModel;
import com.bookms.order.application.servicegateway.IBookServiceGateway;
import com.bookms.order.core.domain.Entity.OrderItems;
import com.bookms.order.core.domain.Entity.OrderType;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.core.domain.Exception.*;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreateOrderUseCase implements BaseUseCase<OrdersModel,Orders>{
    private final IOrderRepository orderRepository;
    private final IBookServiceGateway bookServiceGateway;
    private final CreateBuyOrderUseCase createBuyOrderUseCase;
    private final CreateSellOrderUseCase createSellOrderUseCase;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrdersModel execute(Orders orders) throws OrderExistException {
        orders.setOrderItems(toSet(orders.getOrderItems()));
        if(orders.getOrderNumber() != null){
            Optional<Orders> order = orderRepository.findByOrderNumber(orders.getOrderNumber());
            if(order.isPresent()){
                throw new OrderExistException("order number already exist");
            }
        }
        List<BookModel> bookModels = getBookModels(orders);
        //validOrder
        validOrder(orders,bookModels);

        Orders result = null;
        if(orders.getOrderType() == OrderType.BUY){
            result = createBuyOrderUseCase.execute(orders);
        }else{
            result = createSellOrderUseCase.execute(orders, bookModels);
        }
        OrdersModel ordersModel = getOrdersModel(result);
        //updateQuantity
        for(int i = 0 ; i < orders.getOrderItems().size() ; i++){
            int updateQuantity = getUpdateQuantityByOrderType(orders.getOrderType(),bookModels.get(i).getAvailableQuantity(),orders.getOrderItems().get(i).getTotalQuantity());
            BookModel bookModel = bookServiceGateway.updateQuantity(UpdateQuantityModel.builder()
                    .id(orders.getOrderItems().get(i).getBookId())
                    .type(orders.getOrderType())
                    .quantity(updateQuantity)
                    .build());
            ordersModel.getOrderItems().add(getOrderItemModel(orders.getOrderItems().get(i),bookModels.get(i)));
            if(bookModel == null){
                throw new UpdateBookQuantityFailedException("update book quantity failed");
            }
        }
        return ordersModel;
    }

    private List<OrderItems> toSet(List<OrderItems> orderItems){
        List<OrderItems> result = new ArrayList<>();
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

    private OrderItemModel getOrderItemModel(OrderItems orderItems, BookModel bookModel) {
        return OrderItemModel.builder()
                .name(bookModel.getName())
                .price(bookModel.getPrice())
                .totalQuantity(orderItems.getTotalQuantity())
                .build();
    }

    private OrdersModel getOrdersModel(Orders orders){
        List<OrderItemModel> list = new ArrayList<>();
        return  OrdersModel.builder()
                .orderNumber(orders.getOrderNumber())
                .orderType(orders.getOrderType())
                .customerId(orders.getCustomerId())
                .paymentMethod(orders.getPaymentMethod())
                .status(orders.getStatus())
                .totalPrice(orders.getTotalPrice())
                .orderItems(list)
                .build();
    }

    private void validOrder(Orders order, List<BookModel> bookModels){
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(int i = 0 ; i<order.getOrderItems().size() ; i++){
            BigDecimal price = BigDecimal.ZERO;
            if(!Objects.equals(order.getOrderItems().get(i).getPrice(), bookModels.get(i).getPrice())){
                throw new PriceNotTheSameException(String.format("price for item %s not the same with store",bookModels.get(i).getName()));
            }
            price = bookModels.get(i).getPrice();
            totalPrice = totalPrice.add(price.multiply(BigDecimal.valueOf(order.getOrderItems().get(i).getTotalQuantity())));
        }
        if(!totalPrice.equals(order.getTotalPrice())){
            throw new TotalPriceNotTheSameException(String.format("total price for order %s not correct ",order.getOrderNumber()));
        }
    }

    private List<BookModel> getBookModels(Orders orders){
        Set<Integer> booksId = orders.getOrderItems().stream().map(OrderItems::getBookId).collect(Collectors.toSet());

        List<BookModel> result = bookServiceGateway.findAllBookWithListId(booksId);
        if(result.isEmpty()){
            throw new BookNotFoundException(String.format("books in order not found"));
        }
        return result;
    }
    private int getUpdateQuantityByOrderType(OrderType orderType,int currentQuantity,int orderQuantity){
        if(orderType == OrderType.SELL){
            return currentQuantity - orderQuantity;
        }
        return currentQuantity + orderQuantity;
    }
}
