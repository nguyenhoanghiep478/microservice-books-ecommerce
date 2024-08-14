package com.booksms.marketing.web.controller;

import com.booksms.marketing.interfaceLayer.dto.request.EmailRequest;
import com.booksms.marketing.interfaceLayer.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/marketing")
@RequiredArgsConstructor
public class MarketingController {
    private final EmailService emailService;


    @PostMapping("/send-mail")
    public String sendMail(@RequestBody EmailRequest emailRequest){
        emailService.sendSimpleMail(EmailRequest.builder()
                        .attachment(null)
                        .recipient(emailRequest.getRecipient())
                        .message(emailRequest.getMessage())
                .build());
        return "send success";
    }
}