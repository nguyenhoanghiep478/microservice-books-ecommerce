package com.booksms.book.interfaceLayer.service.book.impl;

import com.booksms.book.application.model.BookSearchCriteria;
import com.booksms.book.application.model.BooksSearchCriteria;
import com.booksms.book.application.usecase.Book.FindUseCase.FindBookUseCase;
import com.booksms.book.application.usecase.Book.FindUseCase.FindBooksUseCase;
import com.booksms.book.core.domain.entity.Book;
import com.booksms.book.interfaceLayer.service.book.IFindBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindBookService implements IFindBookService {
    private final FindBookUseCase findBookUseCase;
    private final FindBooksUseCase findBooksUseCase;

    @Override
    public List<Book> findAll() {
       return findBooksUseCase.execute(null);
    }

    @Override
    public Book findById(int id) {
        return findBookUseCase.execute(BookSearchCriteria.builder()
                .isInStock(true)
                .bookId(id)
                .build()
        );
    }

    @Override
    public Book findByName(String name) {
        return findBookUseCase.execute(BookSearchCriteria.builder()
                .isInStock(true)
                .name(name)
                .build()
        );
    }

    @Override
    public List<Book> findByCategoryId(int categoryId) {
        return findBooksUseCase.execute(BooksSearchCriteria.builder()
                .isInStock(true)
                .categoryId(categoryId)
                .build()
        );
    }

    @Override
    public List<Book> findByCategoryIdAndName(int categoryId, String name) {
        return findBooksUseCase.execute(BooksSearchCriteria.builder()
                .isInStock(true)
                .categoryId(categoryId)
                .name(name)
                .build()
        );
    }

    @Override
    public List<Book> findAllInStock() {
      return findBooksUseCase.execute(BooksSearchCriteria.builder()
              .isInStock(true)
              .build());
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
    public Book findOneLikeNameAndCategoryId(String name, int categoryId) {

        return findBookUseCase.execute(BookSearchCriteria.builder()
                        .name(name)
                        .categoryId(categoryId)
                .build());
    }
}
