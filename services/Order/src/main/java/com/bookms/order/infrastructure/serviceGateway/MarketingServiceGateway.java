package com.bookms.order.infrastructure.serviceGateway;

import com.bookms.order.infrastructure.FeignClient.MarketingClient;
import com.bookms.order.interfaceLayer.DTO.Request.VerifyUserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MarketingServiceGateway {
    private final MarketingClient marketingClient;

    public Boolean validateToken(Long token) {
       log.info(token.toString());
       ResponseEntity<String> response = marketingClient.validateToken(VerifyUserDTO.builder()
                       .email(null)
                       .token(token)
               .build());
       String body = response.getBody();
        assert body != null;
        return body.equals("successful");
    }
}
