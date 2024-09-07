package com.booksms.book.application.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class BooksSearchCriteria{
   private String key;
   private String operation;
   private Object value;
}
