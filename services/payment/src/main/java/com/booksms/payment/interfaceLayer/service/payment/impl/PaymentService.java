package com.booksms.payment.interfaceLayer.service.payment.impl;

import com.booksms.payment.application.model.PaymentModel;
import com.booksms.payment.interfaceLayer.dto.OrderDto;
import com.booksms.payment.interfaceLayer.dto.PaymentDTO;
import com.booksms.payment.interfaceLayer.service.payment.ICreatePaymentService;
import com.booksms.payment.interfaceLayer.service.payment.IPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService implements IPaymentService {
    private final ICreatePaymentService createPaymentService;
    private final KafkaTemplate<String, OrderDto> kafkaTemplate;
    private final AdminClient adminClient;

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
