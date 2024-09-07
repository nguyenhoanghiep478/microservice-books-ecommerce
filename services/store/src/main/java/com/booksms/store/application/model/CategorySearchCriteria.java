package com.booksms.store.application.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategorySearchCriteria {
    private Integer id;
    private String name;
    private String description;
    private Integer booksId;
}
