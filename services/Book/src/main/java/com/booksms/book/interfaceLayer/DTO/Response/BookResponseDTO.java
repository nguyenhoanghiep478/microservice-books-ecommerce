package com.booksms.book.interfaceLayer.DTO.Response;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponseDTO {
    private Integer id;
    private String title;
    private String name;
    private Integer categoryId;
    private Integer distributorId;
    private BigDecimal price;
    private Integer availableQuantity;
    private String image;
    private Integer chapter;
    private Boolean isInStock;
    private Date createdDate;
    private Date lastModifiedDate;
}
