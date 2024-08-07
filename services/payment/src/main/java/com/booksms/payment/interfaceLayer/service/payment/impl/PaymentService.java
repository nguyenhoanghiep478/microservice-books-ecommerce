package com.booksms.payment.interfaceLayer.service.payment.impl;

import com.booksms.payment.application.model.PaymentModel;
import com.booksms.payment.core.domain.entity.Payment;
import com.booksms.payment.interfaceLayer.dto.PaymentDTO;
import com.booksms.payment.interfaceLayer.service.payment.ICreatePaymentService;
import com.booksms.payment.interfaceLayer.service.payment.IPaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
    private final ICreatePaymentService createPaymentService;
    private final ObjectMapper objectMapper;


    @Override
    public void saveToRedis(Long id, PaymentDTO paymentDTO) {
        createPaymentService.saveToRedis(id,paymentDTO);
    }

    @Override
    public PaymentDTO save(Long orderNumber) {
      PaymentModel payment = createPaymentService.save(orderNumber);
      return PaymentDTO.builder()
              .id(payment.getId())
              .method(payment.getPaymentMethod())
              .orderNumber(payment.getOrderNumber())
              .build();
    }
}
