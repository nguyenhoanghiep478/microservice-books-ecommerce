package com.booksms.payment.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaypalCustomModel {
    Double total;
    String currency;
    String method;
    String intent;
    String description;
    Long orderNumber;
    @JsonIgnore
    String cancelUrl;
    @JsonIgnore
    String returnUrl;

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
        this.cancelUrl = "http://localhost:5558/api/v1/payment/paypal/cancel?orderNumber=" + orderNumber;
        this.returnUrl = "http://localhost:5558/api/v1/payment/paypal/success?orderNumber=" + orderNumber;
    }
}
