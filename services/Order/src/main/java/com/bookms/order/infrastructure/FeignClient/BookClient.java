package com.bookms.order.infrastructure.FeignClient;

import com.bookms.order.interfaceLayer.DTO.Request.UpdateQuantityDTO;
import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@FeignClient(name = "book-services",url = "http://localhost:5555/api/v1/book")
public interface BookClient {
    @PostMapping("/anonymous/get-all-by-ids")
    ResponseEntity<ResponseDTO> getBooksByIds(@RequestBody Set<Integer> ids);

    @PostMapping("/update-quantity")
    ResponseEntity<ResponseDTO> updateQuantity(@RequestBody UpdateQuantityDTO updateQuantityDTO);
}
