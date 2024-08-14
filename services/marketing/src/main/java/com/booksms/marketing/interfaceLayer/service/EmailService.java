package com.booksms.marketing.interfaceLayer.service;

import com.booksms.marketing.interfaceLayer.dto.ResponseOrderCreated;
import com.booksms.marketing.interfaceLayer.dto.request.EmailRequest;
import com.booksms.marketing.interfaceLayer.dto.request.NewUserRegister;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;
    private final TemplateEngine templateEngine;
    private final KafkaTemplate<String, ResponseOrderCreated> kafkaTemplate;
    @Async
    @KafkaListener(id = "consumer-order-created",topics = "order-created")
    public void sendSimpleMail(EmailRequest request) {
        if(request.getAttachment()!=null){
            sendMailWithAttachment(request);
        }
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");

            Context context = new Context();
            String htmlBody = templateEngine.process("mailTemplate",context);

            mimeMessageHelper.setTo(request.getRecipient());
            mimeMessageHelper.setSubject("Hello");
            mimeMessageHelper.setText(htmlBody, true);
            mimeMessageHelper.setFrom(from);
            mailSender.send(mimeMessage);
            kafkaTemplate.send("order-created-response",ResponseOrderCreated.builder()
                            .serviceName("MarketingService")
                            .message("successful")
                    .build());
        }catch (Exception e){


        }
    }
    @KafkaListener(id = "consumer-user-register",topics = "UserRegister")
    @Async
    protected void sendMailForNewUser(NewUserRegister request) {
        try{
            System.out.println(request.getRecipient());
            sendMimeMessageMail("mailTemplate",request.getRecipient());
        }catch (Exception e){


        }
    }
    @Async
    protected void sendMimeMessageMail(String template, String receipt){
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");

            Context context = new Context();
            String htmlBody = templateEngine.process(template,context);

            mimeMessageHelper.setTo(receipt);
            mimeMessageHelper.setSubject("Hello");
            mimeMessageHelper.setText(htmlBody, true);
            mimeMessageHelper.setFrom(from);
            mailSender.send(mimeMessage);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private void sendMailWithAttachment(EmailRequest request) {
    }

}
