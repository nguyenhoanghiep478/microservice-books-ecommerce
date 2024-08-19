package com.booksms.marketing.interfaceLayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyUserDTO {
    private String email;
    private Long token;
}
