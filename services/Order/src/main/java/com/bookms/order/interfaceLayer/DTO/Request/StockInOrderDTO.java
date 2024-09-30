package com.bookms.order.interfaceLayer.DTO.Request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class StockInOrderDTO {
    @JsonIgnore
    private Integer productId;
    @JsonIgnore
    private Integer customerId;
    public String employeeName;
    public String productName;
    public int quantity;
    public BigDecimal salePrice;
    public BigDecimal purchasePrice;
    public Date createdDate;

}
