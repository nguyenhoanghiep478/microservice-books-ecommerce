package com.bookms.order.start.Controller;

import com.bookms.order.common.Data.DTO.OrderDTO;
import com.bookms.order.common.Data.DTO.ResponseDTO;
import com.bookms.order.common.Service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService service;
    @GetMapping("/get-all")
    public ResponseEntity<?> GetAllBook() {
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
    public ResponseEntity<?> GetBookById(@PathVariable int id) {
        //get bookDTO
        OrderDTO result = service.findById(id);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("getBookByIdSuccessful"))
                .status(200)
                .result(result)
                .build()
        );
    }
    @PostMapping("/create")
    public ResponseEntity<?> CreateOrder(@RequestBody OrderDTO request) {
        OrderDTO result = service.createOrder(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("getOrderSuccessful"))
                .status(200)
                .result(result)
                .build()
        );
    }
}
