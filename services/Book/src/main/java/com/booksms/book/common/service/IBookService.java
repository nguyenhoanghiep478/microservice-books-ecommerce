package com.booksms.book.common.service;

import com.booksms.book.common.data.dto.Request.BookRequestDTO;
import com.booksms.book.common.data.dto.ShortBookDTO;
import com.booksms.book.common.data.dto.UpdateQuantityDTO;

import java.util.List;

public interface IBookService {
    List<BookRequestDTO> findAll();

    BookRequestDTO findById(int id);
    BookRequestDTO findByName(String name);
    List<BookRequestDTO> findByCategoryId(int categoryId);
    List<BookRequestDTO> findByCategoryIdAndName(int categoryId, String name);
    BookRequestDTO insert(BookRequestDTO request);

    BookRequestDTO updateById(int id, BookRequestDTO request);

    BookRequestDTO deleteById(int id);

    BookRequestDTO updateQuantityById(int id, UpdateQuantityDTO request);

    List<ShortBookDTO> findAllInStock();
}
