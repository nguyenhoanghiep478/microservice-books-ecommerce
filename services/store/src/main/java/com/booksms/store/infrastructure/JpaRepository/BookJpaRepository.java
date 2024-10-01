package com.booksms.store.infrastructure.JpaRepository;

import com.booksms.store.core.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface BookJpaRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByName(String name);

    List<Book> findAllByNameContainingAndCategoryIdAndDistributorIdAndCreatedDateAfter(String name, Integer categoryId, Integer distributorId, Timestamp createdDate);

    List<Book> findAllByCategoryIdAndDistributorId(Integer categoryId, Integer distributorId);

    List<Book> findAllByDistributorIdAndCreatedDateAfter(Integer distributorId, Timestamp createdDate);

    List<Book> findAllByCategoryIdAndCreatedDateAfter(Integer categoryId, Timestamp createdDate);

    Optional<Book> findOneByNameContainingAndCategoryId(String name, Integer categoryId);

    Optional<Book> findOneByIdAndCategoryId(Integer bookId, Integer categoryId);

    Optional<Book> findOneById(Integer bookId);

    Optional<Book> findOneByCategoryId(Integer categoryId);

    List<Book> findAllByNameContainingAndCategoryId(String name, Integer categoryId);
}
