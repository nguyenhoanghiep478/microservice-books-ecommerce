package com.booksms.book.interfaceLayer.service.book;

import com.booksms.book.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.book.core.domain.entity.Book;

public interface ICreateBookService {
    Book insert(BookRequestDTO request);
}
