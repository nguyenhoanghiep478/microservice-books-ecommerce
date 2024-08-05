package com.booksms.book.application.usecase.Book.FindUseCase;

import com.booksms.book.application.model.BookSearchCriteria;
import com.booksms.book.application.usecase.BaseUseCase;
import com.booksms.book.core.domain.entity.Book;
import com.booksms.book.core.domain.exception.BookNotFoundException;
import com.booksms.book.core.domain.repository.IBookRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FindBookUseCase implements BaseUseCase<Book, BookSearchCriteria> {
    private final IBookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public Book execute(@NotNull BookSearchCriteria bookSearchCriteria) {
        Book book = null;
        if (bookSearchCriteria.getName() != null  && bookSearchCriteria.getCategoryId() != null) {
            book = bookRepository.findOneByNameAndCategoryId(bookSearchCriteria.getName(),bookSearchCriteria.getCategoryId()).orElseThrow(
                    () -> new BookNotFoundException(String.format("Book with name: %s,Id: %s,category id: %s not found"
                            , bookSearchCriteria.getName(),bookSearchCriteria.getBookId(),bookSearchCriteria.getCategoryId()))
            );
        } else if (bookSearchCriteria.getBookId() != null && bookSearchCriteria.getCategoryId() != null) {
            book = bookRepository.findOneByBookIdAndCategoryId(bookSearchCriteria.getBookId(),bookSearchCriteria.getCategoryId()).orElseThrow(
                    () -> new BookNotFoundException(String.format("Book with id: %s,category id: %s not found", bookSearchCriteria.getBookId(),bookSearchCriteria.getCategoryId()))
            );
        }
        else if (bookSearchCriteria.getName() != null) {
            book = bookRepository.findOneByName(bookSearchCriteria.getName()).orElseThrow(
                    () -> new BookNotFoundException(String.format("Book with name %s not found", bookSearchCriteria.getName()))
            );
        } else if (bookSearchCriteria.getCategoryId() != null) {
            book = bookRepository.findOneByCategoryId(bookSearchCriteria.getCategoryId()).orElseThrow(
                    () -> new BookNotFoundException(String.format("Book with category id: %s not found", bookSearchCriteria.getBookId()))
            );
        } else if (bookSearchCriteria.getBookId() != null) {
            book = bookRepository.findOneByBookId(bookSearchCriteria.getBookId()).orElseThrow(
                    () -> new BookNotFoundException(String.format("Book with id %s not found", bookSearchCriteria.getBookId()))
            );
        }
        if (book == null) {
            throw new BookNotFoundException(String.format("Book not found with search criteria", bookSearchCriteria.getBookId()));
        }
        if (bookSearchCriteria.getIsInStock() != null) {
            return book.getIsInStock() ? book : null;
        }
        return book;
    }
}
