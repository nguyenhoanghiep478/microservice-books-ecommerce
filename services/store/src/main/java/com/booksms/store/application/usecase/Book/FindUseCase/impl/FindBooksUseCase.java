package com.booksms.store.application.usecase.Book.FindUseCase.impl;

import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.application.usecase.Book.FindUseCase.IFindBookUseCase;
import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.core.domain.exception.BookExpcetion.BookNotFoundException;
import com.booksms.store.core.domain.repository.IBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindBooksUseCase implements IFindBookUseCase {
    private final IBookRepository bookRepository;

    @Override
    public List<Book> execute(List<SearchCriteria> bookSearchCriteria) {
        if(bookSearchCriteria== null) return bookRepository.findAll();
       return bookRepository.search(bookSearchCriteria);
    }

    public Book findById(Integer bookId) {
        return bookRepository.findById(bookId).orElseThrow(
                () -> new BookNotFoundException(String.format("Book with id %s not found", bookId))
        );
    }
}
