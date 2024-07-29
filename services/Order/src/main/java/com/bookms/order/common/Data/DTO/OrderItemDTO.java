package com.bookms.order.common.Data.DTO;

import com.bookms.order.common.Data.Entity.Orders;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Integer orderId;
    private Integer bookId;
    private BigDecimal price;
    private Integer totalQuantity;
}
