package com.booksms.book.interfaceLayer.service.book;

import com.booksms.book.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.book.interfaceLayer.DTO.Request.UpdateQuantityDTO;
import com.booksms.book.core.domain.entity.Book;

public interface IUpdateBookService {
    Book updateById(int id, BookRequestDTO request);
    Book updateQuantityById(int id, UpdateQuantityDTO request);
}
