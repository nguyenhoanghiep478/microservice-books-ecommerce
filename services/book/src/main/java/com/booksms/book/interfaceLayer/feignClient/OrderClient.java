package com.booksms.book.interfaceLayer.feignClient;

import com.booksms.book.interfaceLayer.DTO.Response.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "order-service",url = "http://localhost:5557/api/v1/order")
public interface OrderClient {
    @GetMapping("/get-top-sale")
    ResponseEntity<ResponseDTO> getTopSale();



}
