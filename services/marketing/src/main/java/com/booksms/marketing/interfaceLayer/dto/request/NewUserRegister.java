package com.booksms.marketing.interfaceLayer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRegister {
    private String firstName;
    private String lastName;
    private String recipient;
}
