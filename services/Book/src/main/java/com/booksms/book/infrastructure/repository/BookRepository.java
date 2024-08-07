package com.booksms.book.infrastructure.repository;

import com.booksms.book.core.domain.entity.Book;
import com.booksms.book.core.domain.repository.IBookRepository;
import com.booksms.book.infrastructure.JpaRepository.BookJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepository implements IBookRepository {
    private final BookJpaRepository bookJpaRepository;
    @Override
    public Optional<Book> findByName(String name) {
        return bookJpaRepository.findByName(name);
    }

    @Override
    public Optional<Book> findById(Integer id) {
        return bookJpaRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookJpaRepository.findAll();
    }

    @Override
    public List<Book> findAllLikeNameAndCategoryIdAndDistributorIdAndAfterCreatedDate(String name, Integer categoryId, Integer distributorId, Timestamp createdDate) {
        return bookJpaRepository.findAllByNameContainingAndCategoryIdAndDistributorIdAndCreatedDateAfter(
                name, categoryId, distributorId, createdDate
        );
    }


    @Override
    public List<Book> findAllByCategoryIdAndDistributorId(Integer categoryId, Integer distributorId) {
        return bookJpaRepository.findAllByCategoryIdAndDistributorId(categoryId,distributorId);
    }

    @Override
    public List<Book> findALlByDistributorIdAndCreatedDate(Integer distributorId, Timestamp createdDate) {
        return bookJpaRepository.findAllByDistributorIdAndCreatedDateAfter(distributorId,createdDate);
    }

    @Override
    public List<Book> findAllByCategoryIdAndCreatedDate(Integer categoryId, Timestamp createdDate) {
        return bookJpaRepository.findAllByCategoryIdAndCreatedDateAfter(categoryId,createdDate);
    }

    @Override
    public Optional<Book> findOneByNameAndCategoryId(String name, Integer categoryId) {
        return bookJpaRepository.findOneByNameContainingAndCategoryId(name,categoryId);
    }

    @Override
    public Optional<Book> findOneByBookIdAndCategoryId(Integer bookId,Integer categoryId) {
        return bookJpaRepository.findOneByIdAndCategoryId(bookId,categoryId);
    }

    @Override
    public Optional<Book> findOneByBookId(Integer bookId) {
        return bookJpaRepository.findOneById(bookId);
    }

    @Override
    public Optional<Book> findOneByCategoryId(Integer categoryId) {
        return bookJpaRepository.findOneByCategoryId(categoryId);
    }

    @Override
    public Optional<Book> save(Book entity) {
        return Optional.of(bookJpaRepository.save(entity));
    }

    @Override
    public Optional<Book> findOneByName(String name) {
        return bookJpaRepository.findByName(name);
    }

    @Override
    public List<Book> findAllLikeNameAndCategoryId(String name, Integer categoryId) {
        return bookJpaRepository.findAllByNameContainingAndCategoryId(name,categoryId);
    }

}
