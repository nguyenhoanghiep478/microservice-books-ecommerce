package com.booksms.store.interfaceLayer.servicegateway;

import com.booksms.store.interfaceLayer.DTO.Response.ResponseDTO;
import com.booksms.store.interfaceLayer.DTO.Response.TopSalesDTO;
import java.util.List;

import com.booksms.store.interfaceLayer.feignClient.OrderClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderServiceGateway {
    private final OrderClient orderClient;
    private final ModelMapper modelMapper;

    public List<TopSalesDTO> getTopSales() {
        ResponseEntity<ResponseDTO> response = this.orderClient.getTopSale();
        ResponseDTO responseDTO = (ResponseDTO)response.getBody();
        List<Object> objects = (List)responseDTO.getResult();
        return objects.stream().map((item) -> {
            return (TopSalesDTO)this.modelMapper.map(item, TopSalesDTO.class);
        }).toList();
    }


}