package com.booksms.book.interfaceLayer.servicegateway;

import com.booksms.book.interfaceLayer.DTO.Response.ResponseDTO;
import com.booksms.book.interfaceLayer.DTO.Response.TopSalesDTO;
import com.booksms.book.interfaceLayer.feignClient.OrderClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderServiceGateway {

    private final OrderClient orderClient;
    private final ModelMapper modelMapper;


    public List<TopSalesDTO> getTopSales(){
        ResponseEntity<ResponseDTO> response = orderClient.getTopSale();
        ResponseDTO responseDTO = response.getBody();
        List<Object> objects = (List<Object>) responseDTO.getResult();
        return objects.stream()
                .map(item -> modelMapper.map(item, TopSalesDTO.class))
                .toList();
    }
}
