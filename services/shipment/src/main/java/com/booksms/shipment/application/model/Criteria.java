package com.booksms.shipment.application.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Criteria {
    private String key;
    private String operator;
    private Object value;
}
