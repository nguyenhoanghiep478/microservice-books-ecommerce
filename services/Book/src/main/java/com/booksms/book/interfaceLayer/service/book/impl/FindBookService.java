package com.booksms.book.interfaceLayer.service.book.impl;

import com.booksms.book.application.model.BooksSearchCriteria;
import com.booksms.book.application.usecase.Book.FindUseCase.FindBooksUseCase;
import com.booksms.book.core.domain.entity.Book;
import com.booksms.book.interfaceLayer.service.book.IFindBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindBookService implements IFindBookService {
    private final FindBooksUseCase findBooksUseCase;

    @Override
    public List<Book> findAll() {
       return findBooksUseCase.execute(null);
    }

    @Override
    public Book findById(int id) {
        BooksSearchCriteria findById = BooksSearchCriteria.builder()
                .key("id")
                .operation(":")
                .value(id)
                .build();
        BooksSearchCriteria withIsInStock = BooksSearchCriteria.builder()
                .key("isInStock")
                .operation(":")
                .value(true)
                .build();
        return findBooksUseCase.execute(List.of(findById,withIsInStock)).get(0);
    }

    @Override
    public Book findByName(String name) {
        BooksSearchCriteria findByName = BooksSearchCriteria.builder()
                .key("name")
                .operation(":")
                .value(name)
                .build();
        BooksSearchCriteria withIsInStock = BooksSearchCriteria.builder()
                .key("isInStock")
                .operation(":")
                .value(true)
                .build();
        return findBooksUseCase.execute(List.of(findByName,withIsInStock)).get(0);
    }

    @Override
    public List<Book> findByCategoryId(int categoryId) {
        BooksSearchCriteria findByCategoryId = BooksSearchCriteria.builder()
                .key("categoryId")
                .operation(":")
                .value(categoryId)
                .build();
        BooksSearchCriteria withIsInStock = BooksSearchCriteria.builder()
                .key("isInStock")
                .operation(":")
                .value(true)
                .build();
        return findBooksUseCase.execute(List.of(findByCategoryId,withIsInStock));
    }

    @Override
    public List<Book> findByCategoryIdAndName(int categoryId, String name) {
        BooksSearchCriteria findByName = BooksSearchCriteria.builder()
                .key("name")
                .operation(":")
                .value(name)
                .build();
        BooksSearchCriteria findByCategoryId = BooksSearchCriteria.builder()
                .key("categoryId")
                .operation(":")
                .value(categoryId)
                .build();
        BooksSearchCriteria withIsInStock = BooksSearchCriteria.builder()
                .key("isInStock")
                .operation(":")
                .value(true)
                .build();
        return findBooksUseCase.execute(List.of(findByName,findByCategoryId,withIsInStock));
    }

    @Override
    public List<Book> findAllInStock() {
        BooksSearchCriteria withIsInStock = BooksSearchCriteria.builder()
                .key("isInStock")
                .operation(":")
                .value(true)
                .build();
        return findBooksUseCase.execute(List.of(withIsInStock));
    }

    @Override
    public List<Book> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public boolean existById(int id) {
        return this.findById(id) != null;
    }

    @Override
    public boolean existByName(String name) {
        return this.findByName(name)!= null;
    }

    @Override
    public List<Book> findOneLikeNameAndCategoryId(String name, int categoryId) {
        BooksSearchCriteria findByName = BooksSearchCriteria.builder()
                .key("name")
                .operation("LIKE")
                .value(String.valueOf(name))
                .build();
        BooksSearchCriteria findByCategoryId = BooksSearchCriteria.builder()
                .key("categoryId")
                .operation(":")
                .value(String.valueOf(categoryId))
                .build();
        return findBooksUseCase.execute(List.of(findByName,findByCategoryId));
    }
}
