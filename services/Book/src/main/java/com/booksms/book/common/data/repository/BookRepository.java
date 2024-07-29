package com.booksms.book.common.data.repository;

import com.booksms.book.common.data.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByName(String name);
    List<Book> findAllByCategoryId(int category_id);
    List<Book> findByIsInStockTrue();
}
