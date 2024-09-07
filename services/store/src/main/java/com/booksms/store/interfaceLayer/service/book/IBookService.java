package com.booksms.store.interfaceLayer.service.book;

import com.booksms.store.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.store.interfaceLayer.DTO.Request.ShortBookDTO;
import com.booksms.store.interfaceLayer.DTO.Request.UpdateQuantityDTO;
import com.booksms.store.interfaceLayer.DTO.Response.BookResponseDTO;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IBookService {

    BookRequestDTO insert(BookRequestDTO request) throws IOException;

    BookRequestDTO updateById(int id, BookRequestDTO request) throws IOException;

    BookRequestDTO deleteById(int id);

    BookRequestDTO updateQuantityById(int id, UpdateQuantityDTO request) throws IOException;

    List<BookRequestDTO> findByCategoryIdAndName(int categoryId, String name);

    List<BookRequestDTO> findByCategoryId(int categoryId);

    List<BookRequestDTO> findAllLikeNameAndCategoryId(int id, String name);

    BookRequestDTO findByName(String name);

    BookResponseDTO findById(int id) throws IOException;

    List<ShortBookDTO> findAllInStock();

    List<BookResponseDTO> findAll() throws IOException;
    List<BookRequestDTO> findAll(Pageable pageable);
    List<ShortBookDTO> findAllByIds(Set<Integer> ids);

    List<BookResponseDTO> findTopSales() throws IOException;
}
