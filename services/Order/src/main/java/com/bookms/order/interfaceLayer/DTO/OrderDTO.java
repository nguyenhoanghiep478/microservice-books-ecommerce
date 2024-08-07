package com.bookms.order.interfaceLayer.DTO;

import com.bookms.order.core.domain.Entity.OrderType;
import com.bookms.order.core.domain.Entity.Status;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer id;
    private Long orderNumber;
    private OrderType orderType;
    private Status status;
    private int customerId;
    private BigDecimal totalPrice;
    private List<OrderItemDTO> orderItems;
    private String paymentMethod;
}
