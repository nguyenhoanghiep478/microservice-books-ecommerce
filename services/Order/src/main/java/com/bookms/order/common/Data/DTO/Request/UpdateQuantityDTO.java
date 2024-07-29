package com.bookms.order.common.Data.DTO.Request;

import com.bookms.order.common.Data.Entity.OrderType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateQuantityDTO {
    private int quantity;
    private OrderType type;
}
