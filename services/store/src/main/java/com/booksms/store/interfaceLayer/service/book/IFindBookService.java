package com.booksms.store.interfaceLayer.service.book;

import com.booksms.store.core.domain.entity.Book;
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

    List<Book> findOneLikeNameAndCategoryId(String name, int categoryId);

    List<Book> findTopSales();
}
