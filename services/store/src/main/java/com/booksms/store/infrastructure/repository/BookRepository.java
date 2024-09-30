package com.booksms.store.infrastructure.repository;

import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.core.domain.repository.IBookRepository;
import com.booksms.store.infrastructure.JpaRepository.BookJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepository extends AbstractRepository<Book> implements IBookRepository{
    private final BookJpaRepository bookJpaRepository;


    public BookRepository(@Qualifier("bookClass") Class<Book> entityClass, BookJpaRepository bookJpaRepository1) {
        super(entityClass);
        this.bookJpaRepository = bookJpaRepository1;
    }


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

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookJpaRepository.findAll(pageable);
    }

    @Override
    public List<Book> search(List<SearchCriteria> bookSearchCriteria) {
       return abstractSearch(bookSearchCriteria);
    }

}
