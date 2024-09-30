package com.booksms.distributor.interfaceLayer.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistributorDTO {
    private Integer id;
    private String name;
    private String slug;
    private String email;
    private String phone;
    private boolean active;
    private String licenseId;
}
