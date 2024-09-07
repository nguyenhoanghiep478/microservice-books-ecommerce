package com.booksms.store.interfaceLayer.service.book;

import com.booksms.store.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.store.interfaceLayer.DTO.Request.UpdateQuantityDTO;
import com.booksms.store.core.domain.entity.Book;

import java.io.IOException;

public interface IUpdateBookService {
    Book updateById(int id, BookRequestDTO request) throws IOException;
    Book updateQuantityById(int id, UpdateQuantityDTO request) throws IOException;
}
