package com.booksms.book.interfaceLayer.service.book;

import com.booksms.book.core.domain.entity.Book;

import java.util.List;

public interface IFindBookService {
    List<Book> findAll();
    Book findById(int id);
    Book findByName(String name);
    List<Book> findByCategoryId(int categoryId);
    List<Book> findByCategoryIdAndName(int categoryId, String name);
    List<Book> findAllInStock();
    boolean existById(int id);
    boolean existByName(String name);

    Book findOneLikeNameAndCategoryId(String name, int categoryId);
}
