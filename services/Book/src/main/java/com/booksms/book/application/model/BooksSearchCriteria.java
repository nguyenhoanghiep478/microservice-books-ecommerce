package com.booksms.book.application.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;

@Data
@Builder
public class BooksSearchCriteria{
   private String key;
   private String operation;
   private Object value;
}
