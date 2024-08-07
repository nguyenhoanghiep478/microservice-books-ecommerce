package com.booksms.payment.interfaceLayer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePayment {
    private Integer paymentId;
    private Long orderNumber;
    private String paymentMethod;
}
