package com.bookms.order.common.Data.DTO;

import com.bookms.order.common.Data.Entity.OrderType;
import com.bookms.order.common.Data.Entity.Status;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderNumber;
    private OrderType orderType;
    private Status status;
    private int customerId;
    private BigDecimal totalPrice;
    private List<OrderItemDTO> orderItems;
    private String paymentMethod;
}
