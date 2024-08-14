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
import com.bookms.order.core.domain.Entity.Status;
import com.bookms.order.core.domain.Exception.*;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreateOrderUseCase implements BaseUseCase<OrdersModel,OrdersModel>{
    private final IOrderRepository orderRepository;
    private final IBookServiceGateway bookServiceGateway;
    private final CreateBuyOrderUseCase createBuyOrderUseCase;
    private final CreateSellOrderUseCase createSellOrderUseCase;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrdersModel execute(OrdersModel orderModel) throws OrderExistException {
        orderModel.setStatus(Status.COMPLETED);
        Orders orders = toOrder(orderModel);

        Orders result = null;
        if(orders.getOrderType() == OrderType.BUY){
            result = createBuyOrderUseCase.execute(orders);
        }else{
            result = createSellOrderUseCase.execute(orders, orderModel.getBookModels());
        }

        //updateQuantity
//        for(int i = 0 ; i < orders.getOrderItems().size() ; i++){
//            int updateQuantity = getUpdateQuantityByOrderType(orders.getOrderType(),orderModel.getBookModels().get(i).getAvailableQuantity(),orders.getOrderItems().get(i).getTotalQuantity());
//            BookModel bookModel = bookServiceGateway.updateQuantity(UpdateQuantityModel.builder()
//                    .id(orders.getOrderItems().get(i).getBookId())
//                    .type(orders.getOrderType())
//                    .quantity(updateQuantity)
//                    .build());
//            if(bookModel == null){
//                throw new UpdateBookQuantityFailedException("update book quantity failed");
//            }
//        }
        return orderModel;
    }

    private Orders toOrder(OrdersModel ordersModel){
        Orders orders = new Orders();
        orders.setOrderType(ordersModel.getOrderType());
        orders.setTotalPrice(ordersModel.getTotalPrice());
        orders.setStatus(ordersModel.getStatus());
        orders.setCustomerId(ordersModel.getCustomerId());
        orders.setPaymentMethod(ordersModel.getPaymentMethod());
        orders.setOrderNumber(ordersModel.getOrderNumber());
        orders.setPaymentId(ordersModel.getPaymentId());
        orders.setOrderItems(ordersModel.getOrderItems().stream().map(item -> toOrderItems(item,orders)).toList());
        return orders;
    }
    private OrderItems toOrderItems(OrderItemModel orderItemModel,Orders orders){
        OrderItems orderItems = new OrderItems();
        orderItems.setBookId(orderItemModel.getBookId());
        orderItems.setTotalQuantity(orderItemModel.getTotalQuantity());
        orderItems.setPrice(orderItemModel.getPrice());
        orderItems.setOrders(orders);
        return orderItems;
    }

    private int getUpdateQuantityByOrderType(OrderType orderType,int availableQuantity,int totalQuantity){
        if(orderType == OrderType.BUY){
            return availableQuantity + totalQuantity;
        }else{
            return availableQuantity - totalQuantity;
        }
    }

}
