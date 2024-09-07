package com.bookms.order.infrastructure.FeignClient;

import com.bookms.order.config.FeignConfig;
import com.bookms.order.interfaceLayer.DTO.Request.VerifyUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "marketing-client", url = "http://localhost:5559/api/v1/marketing", configuration = FeignConfig.class)
public interface MarketingClient {
    @PostMapping("/verify-token")
   ResponseEntity<String> validateToken(@RequestBody VerifyUserDTO verifyUserDTO);

}
