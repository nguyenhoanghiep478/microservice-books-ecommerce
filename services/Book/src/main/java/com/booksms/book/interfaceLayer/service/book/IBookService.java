package com.booksms.book.interfaceLayer.service.book;

import com.booksms.book.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.book.interfaceLayer.DTO.Request.ShortBookDTO;
import com.booksms.book.interfaceLayer.DTO.Request.UpdateQuantityDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface IBookService {

    BookRequestDTO insert(BookRequestDTO request);

    BookRequestDTO updateById(int id, BookRequestDTO request);

    BookRequestDTO deleteById(int id);

    BookRequestDTO updateQuantityById(int id, UpdateQuantityDTO request);

    List<BookRequestDTO> findByCategoryIdAndName(int categoryId, String name);

    List<BookRequestDTO> findByCategoryId(int categoryId);

    List<BookRequestDTO> findAllLikeNameAndCategoryId(int id, String name);

    BookRequestDTO findByName(String name);

    BookRequestDTO findById(int id);

    List<ShortBookDTO> findAllInStock();

    List<BookRequestDTO> findAll();
    List<BookRequestDTO> findAll(Pageable pageable);
    List<ShortBookDTO> findAllByIds(Set<Integer> ids);
}
