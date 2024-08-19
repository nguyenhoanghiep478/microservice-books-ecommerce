package com.booksms.book.infrastructure.repository;

import com.booksms.book.application.model.BooksSearchCriteria;
import com.booksms.book.core.domain.entity.Book;
import com.booksms.book.core.domain.repository.IBookRepository;
import com.booksms.book.infrastructure.JpaRepository.BookJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepository implements IBookRepository {
    private final BookJpaRepository bookJpaRepository;
    @PersistenceContext
    private EntityManager entityManager;

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
    public List<Book> search(List<BooksSearchCriteria> bookSearchCriteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = builder.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();
        for(BooksSearchCriteria criteria : bookSearchCriteria) {
            switch (criteria.getOperation()){
                case ":":
                    predicates.add(builder.equal(root.get(criteria.getKey()),(Comparable) criteria.getValue()));
                    break;
                case ">":
                    predicates.add(builder.greaterThan(root.get(criteria.getKey()),(Comparable) criteria.getValue()));
                    break;
                case "<":
                    predicates.add(builder.lessThan(root.get(criteria.getKey()), (Comparable) criteria.getValue()));
                case "LIKE":
                    predicates.add(builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%"));
                    break;
            }
        }
        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }

}
