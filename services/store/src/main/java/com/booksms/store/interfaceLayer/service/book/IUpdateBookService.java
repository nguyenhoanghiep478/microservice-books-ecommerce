package com.booksms.book.interfaceLayer.service.book;

import com.booksms.book.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.book.interfaceLayer.DTO.Request.UpdateQuantityDTO;
import com.booksms.book.core.domain.entity.Book;

import java.io.IOException;

public interface IUpdateBookService {
    Book updateById(int id, BookRequestDTO request) throws IOException;
    Book updateQuantityById(int id, UpdateQuantityDTO request) throws IOException;
}
