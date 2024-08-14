package com.booksms.book.interfaceLayer.service.book;

import com.booksms.book.core.domain.entity.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFindBookService {
    List<Book> findAll();
    Book findById(int id);
    Book findByName(String name);
    List<Book> findByCategoryId(int categoryId);
    List<Book> findByCategoryIdAndName(int categoryId, String name);
    List<Book> findAllInStock();
    List<Book> findAll(Pageable pageable);

    boolean existById(int id);
    boolean existByName(String name);

    Book findOneLikeNameAndCategoryId(String name, int categoryId);
}
