package com.bookms.order.infrastructure.serviceGateway;

import com.bookms.order.infrastructure.FeignClient.MarketingClient;
import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import com.booksms.marketing.interfaceLayer.dto.VerifyUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MarketingServiceGateway {
    private final MarketingClient marketingClient;

    public Boolean validateToken(Long token) {
       ResponseEntity<String> response = marketingClient.validateToken(VerifyUserDTO.builder()
                       .email(null)
                       .token(token)
               .build());
       String body = response.getBody();
        assert body != null;
        return body.equals("successful");
    }
}
