package com.bookms.order.interfaceLayer.DTO;

import lombok.Data;

@Data
public class ResponsePayment {
    private Long orderNumber;
    private String paymentMethod;
    private Integer paymentId;
}
