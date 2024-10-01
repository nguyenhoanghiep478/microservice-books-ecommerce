package com.booksms.authentication.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String password;
    private Boolean isVerified;
    private Boolean isFirstVisit;
    private Boolean isBlocked;
    private String roleName;
    private String image;
}
