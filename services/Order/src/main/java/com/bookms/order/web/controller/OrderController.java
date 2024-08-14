package com.bookms.order.web.controller;

import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.model.PaymentModel;
import com.bookms.order.core.domain.Entity.OrderType;
import com.bookms.order.infrastructure.FeignClient.PaymentClient;
import com.bookms.order.interfaceLayer.DTO.OrderDTO;
import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import com.bookms.order.interfaceLayer.DTO.ResponsePayment;
import com.bookms.order.interfaceLayer.service.IOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService service;
    private final PaymentClient paymentClient;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, OrdersModel> kafkaTemplate;

    @GetMapping("/get-all")
    public ResponseEntity<?> GetAll() {
        //get listDTO
        List<OrderDTO> results = service.findAll();
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("getAllBookSuccessful"))
                .status(201)
                //return list dto
                .result(results)
                .build());
    }
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> GetById(@PathVariable int id) {
        //get bookDTO
        OrderDTO result = service.findById(id);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("getBookByIdSuccessful"))
                .status(200)
                .result(result)
                .build()
        );
    }
//    @PostMapping("/create")
//    public ResponseEntity<?> CreateOrder(@RequestBody OrderDTO request) {
//        OrderDTO result = service.createOrder(request);
//        return ResponseEntity.ok(ResponseDTO.builder()
//                .message(Arrays.asList("getOrderSuccessful"))
//                .status(200)
//                .result(result)
//                .build()
//        );
//    }



    @PostMapping("/pay-order")
    public ResponseEntity<?> PayOrder(@RequestBody OrderDTO request) {
        PaymentModel paymentModel = service.prePay(request);
        return paymentClient.create(paymentModel);
    }


    @PostMapping("/success-payment")
    public ResponseEntity<?> SuccessPayment(@RequestBody ResponsePayment responsePayment) {
        OrdersModel orderIsPaidSucceed = service.afterPay(responsePayment.getOrderNumber());
        orderIsPaidSucceed.setPaymentMethod(responsePayment.getPaymentMethod());
        orderIsPaidSucceed.setPaymentId(responsePayment.getPaymentId());
        OrderDTO result = service.createOrder(orderIsPaidSucceed);
        service.completeOrder(responsePayment.getOrderNumber());
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("getOrderSuccessful"))
                .status(200)
                .result(result)
                .build()
        );
    }
}
