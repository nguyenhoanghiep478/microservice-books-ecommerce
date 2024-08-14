package com.booksms.book.core.domain.repository;

import com.booksms.book.core.domain.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface IBookRepository {
    Optional<Book> findByName(String name);
    Optional<Book> findById(Integer id);
    List<Book> findAll();
    List<Book> findAllLikeNameAndCategoryIdAndDistributorIdAndAfterCreatedDate(String name, Integer categoryId,Integer distributorId, Timestamp createdDate);

    List<Book> findAllByCategoryIdAndDistributorId(Integer categoryId, Integer distributorId);

    List<Book> findALlByDistributorIdAndCreatedDate(Integer distributorId, Timestamp createdDate);

    List<Book> findAllByCategoryIdAndCreatedDate(Integer categoryId, Timestamp createdDate);

    Optional<Book> findOneByNameAndCategoryId(String name, Integer categoryId);

    Optional<Book> findOneByBookIdAndCategoryId(Integer bookId,Integer categoryId);

    Optional<Book> findOneByBookId(Integer bookId);

    Optional<Book> findOneByCategoryId(Integer categoryId);

    Optional<Book> save(Book entity);

    Optional<Book> findOneByName(String name);

    List<Book> findAllLikeNameAndCategoryId(String name, Integer categoryId);

    Page<Book> findAll(Pageable pageable);
}
