package com.booksms.book.application.model;

import com.booksms.book.core.domain.entity.Book;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class CategorySearchCriteria {
    private Integer id;
    private String name;
    private String description;
    private Integer booksId;
}
