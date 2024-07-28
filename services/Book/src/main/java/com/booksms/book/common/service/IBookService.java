package com.booksms.book.common.service;

import com.booksms.book.common.data.dto.BookDTO;
import com.booksms.book.common.data.dto.UpdateQuantityDTO;

import java.util.List;

public interface IBookService {
    List<BookDTO> findAll();

    BookDTO findById(int id);
    BookDTO findByName(String name);
    List<BookDTO> findByCategoryId(int categoryId);
    List<BookDTO> findByCategoryIdAndName(int categoryId, String name);
    BookDTO insert(BookDTO request);

    BookDTO updateById(int id,BookDTO request);

    BookDTO deleteById(int id);

    BookDTO updateQuantityById(int id, UpdateQuantityDTO request);
}
