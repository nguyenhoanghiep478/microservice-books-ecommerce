package com.booksms.book.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchCriteria {
    private String name;
    private Integer bookId;
    private Integer categoryId;
    private Boolean isInStock;

}
