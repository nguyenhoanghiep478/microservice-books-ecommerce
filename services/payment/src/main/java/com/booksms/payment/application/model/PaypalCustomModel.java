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
    @JsonIgnore
    final String cancelUrl = "http://localhost:5558/api/v1/payment/paypal/cancel";
    @JsonIgnore
    final String returnUrl = "http://localhost:5558/api/v1/payment/paypal/success";
}
