package com.booksms.book.interfaceLayer.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseOrderCreated {
     private String serviceName;
    private String message;
}
