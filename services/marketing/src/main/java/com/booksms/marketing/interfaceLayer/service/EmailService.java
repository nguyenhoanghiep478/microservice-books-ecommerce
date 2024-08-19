package com.booksms.marketing.interfaceLayer.service;

import com.booksms.marketing.core.domain.exception.InvalidToken;
import com.booksms.marketing.interfaceLayer.dto.ResponseOrderCreated;
import com.booksms.marketing.interfaceLayer.dto.VerifyUserDTO;
import com.booksms.marketing.interfaceLayer.dto.request.EmailRequest;
import com.booksms.marketing.interfaceLayer.dto.request.NewUserRegister;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService implements IEmailService {
    private final JavaMailSender mailSender;
    private final RedisNewUserService redisNewUserService;
    @Value("${spring.mail.username}")
    private String from;
    private final TemplateEngine templateEngine;
    private final KafkaTemplate<String, ResponseOrderCreated> kafkaTemplate;
    private final KafkaTemplate<String, NewUserRegister> verifyKafkaTemplate;

    @KafkaListener(id = "consumer-order-created",topics = "order-created")
    @Override
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
            log.error(e.getMessage());
        }
    }
    @KafkaListener(id = "consumer-user-register",topics = "UserRegister")
    @Async
    protected void sendMailForNewUser(NewUserRegister request) {
        try{
            if(request.getIsVerified() && request.getIsFirstVisit()){
                sendMimeMessageMail("mailTemplate",request.getRecipient(),new Context(),"Welcome new user");
            }
        }catch (Exception e){
            log.error(e.getMessage());

        }
    }
    protected void sendMimeMessageMail(String template, String receipt,Context context,String subject){
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");

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

    @KafkaListener(id = "consumer-verify-token",topics = "UserRegister")
    private void sendVerifyToken(NewUserRegister request) {
        Random random = new Random();
        if(!request.getIsVerified()){
             log.info("sending message ... ");
            Long generateVerifyToken = 100000 + random.nextLong(900000);
            Context context = new Context();
            context.setVariable("username",request.getFirstName() + " " + request.getLastName());
            context.setVariable("verification_code", generateVerifyToken);
            context.setVariable("expiration_time", "15 minutes");


            sendMimeMessageMail("VerifyTemplate", request.getRecipient(), context,"verify email");
            Instant now = Instant.now();
            request.setLastTimeSendToken(Timestamp.from(now));


            Duration oneHours = Duration.ofHours(1);
            this.redisNewUserService.save(generateVerifyToken,request,oneHours);

        }
    }

    @Override
    public String verifyToken(VerifyUserDTO verifyUserDTO) {
        NewUserRegister verifyUser = redisNewUserService.get(verifyUserDTO.getToken());
        if(verifyUser == null){
            throw new InvalidToken("invalid token , please try again");
        }

        Instant now = Instant.now();
        Timestamp lastTimeSendToken = verifyUser.getLastTimeSendToken();
        long diff = ChronoUnit.MINUTES.between(now,lastTimeSendToken.toInstant());
        if(diff > 15){
            sendVerifyToken(verifyUser);
            return "resend token";
        }

        verifyUser.setIsVerified(true);
        verifyKafkaTemplate.send("UserRegister",verifyUser);
        verifyUser.setIsFirstVisit(false);
        verifyKafkaTemplate.send("UserRegisterResponse",verifyUser);


        redisNewUserService.delete(verifyUserDTO.getToken());
        return "successful";
    }
}
