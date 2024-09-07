package com.booksms.store.interfaceLayer.service.book;

import com.booksms.store.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.store.core.domain.entity.Book;

import java.io.IOException;

public interface ICreateBookService {
    Book insert(BookRequestDTO request) throws IOException;
}
