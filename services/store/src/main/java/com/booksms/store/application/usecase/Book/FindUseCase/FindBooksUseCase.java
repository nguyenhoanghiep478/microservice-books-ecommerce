package com.booksms.book.application.usecase.Book.FindUseCase;

import com.booksms.book.application.model.BooksSearchCriteria;
import com.booksms.book.application.usecase.BaseUseCase;
import com.booksms.book.application.usecase.Category.FindCategoryUseCase;
import com.booksms.book.core.domain.entity.Book;
import com.booksms.book.core.domain.exception.BookNotFoundException;
import com.booksms.book.core.domain.repository.IBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindBooksUseCase implements BaseUseCase<List<Book>, List<BooksSearchCriteria>> {
    private final IBookRepository bookRepository;
    @Override
    public List<Book> execute(List<BooksSearchCriteria> bookSearchCriteria) {
        if(bookSearchCriteria== null) return bookRepository.findAll();
       return bookRepository.search(bookSearchCriteria);
    }
}
