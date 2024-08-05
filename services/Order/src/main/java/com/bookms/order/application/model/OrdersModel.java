package com.bookms.order.application.model;

import com.bookms.order.core.domain.Entity.OrderType;
import com.bookms.order.core.domain.Entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersModel {
    private Long orderNumber;
    private OrderType orderType;
    private Status status;
    private int customerId;
    private BigDecimal totalPrice;
    private List<OrderItemModel> orderItems;
    private String paymentMethod;
}
