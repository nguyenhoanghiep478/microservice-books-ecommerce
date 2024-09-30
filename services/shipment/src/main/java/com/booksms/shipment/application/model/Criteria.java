package com.booksms.shipment.application.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Criteria {
    private String key;
    private String operator;
    private Object value;
}
