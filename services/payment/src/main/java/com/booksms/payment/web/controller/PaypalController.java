package com.booksms.payment.web.controller;

import com.booksms.payment.application.model.PaypalCustomModel;
import com.booksms.payment.application.model.ResponsePaymentPageModel;
import com.booksms.payment.infrastructure.FeignClient.OrderClient;
import com.booksms.payment.infrastructure.servicegateway.PaypalGateway;
import com.booksms.payment.interfaceLayer.dto.PaymentDTO;
import com.booksms.payment.interfaceLayer.dto.ResponseDTO;
import com.booksms.payment.interfaceLayer.dto.ResponsePayment;
import com.booksms.payment.interfaceLayer.service.RedisService.OrderRedisService;
import com.booksms.payment.interfaceLayer.service.payment.IPaymentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment/paypal")
@Slf4j
public class PaypalController {
    private final PaypalGateway paypalGateway;
    private final OrderClient orderClient;
    private final OrderRedisService orderRedisService;
    private final IPaymentService paymentService;
    @GetMapping("/")
    public String HomePage(){
        return "index";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO) {
        try{
            Payment payment = paypalGateway.createPayment(PaypalCustomModel.builder()
                            .total(paymentDTO.getTotal())
                            .currency(paymentDTO.getCurrency())
                            .method(paymentDTO.getMethod())
                            .intent(paymentDTO.getIntent())
                            .description(paymentDTO.getDescription())
                    .build(),ResponsePayment.builder()
                            .paymentMethod(paymentDTO.getMethod())
                            .orderNumber(paymentDTO.getOrderNumber())
                    .build());
            paymentService.saveToRedis(paymentDTO.getOrderNumber(),paymentDTO);
            for (Links link : payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return ResponseEntity.ok(ResponseDTO.builder()
                                    .status(200)
                                    .message(List.of("created"))
                                    .result(ResponsePaymentPageModel.builder()
                                            .status("created")
                                            .message(link.getHref())
                                            .build())
                            .build());
                }
            }
        }catch (PayPalRESTException e){
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("created"))
                .result(ResponsePaymentPageModel.builder()
                        .status("created")
                        .message("http://localhost:5558/api/v1/payment/error")
                        .build())
                .build());
    }

    @GetMapping("/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ) {
        try{
            ResponsePayment responsePayment = orderRedisService.getValue(paymentId);
            PaymentDTO paymentDTO =  paymentService.save(responsePayment.getOrderNumber());
            responsePayment.setPaymentId(paymentDTO.getId());
            orderClient.callBack(responsePayment);

            Payment payment = paypalGateway.excutePayment(paymentId,payerId);
            orderRedisService.deleteValue(paymentId);
            if(payment.getState().equals("approved")){
               return "paymentSuccess";
            }

        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }
        return "paymentSuccess";
    }
    @GetMapping("/cancel")
    public String paymentCancel(){
        return "paymentCancel";
    }
    @GetMapping("/error")
    public String paymentError(){
        return "paymentError";
    }
}
