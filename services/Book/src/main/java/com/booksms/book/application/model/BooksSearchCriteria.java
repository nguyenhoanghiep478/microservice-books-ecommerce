package com.booksms.book.application.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class BooksSearchCriteria {
   private String name;
   private Boolean isInStock;
   private Integer authorId;
   private Integer categoryId;
   private Integer distributorId;
   private Timestamp createdDate;
   private Timestamp updatedDate;
}
