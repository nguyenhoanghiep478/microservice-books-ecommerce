package com.booksms.store.interfaceLayer.DTO.Response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProfitDTO {
    private Integer bookId;
    private BigDecimal profit;
}
