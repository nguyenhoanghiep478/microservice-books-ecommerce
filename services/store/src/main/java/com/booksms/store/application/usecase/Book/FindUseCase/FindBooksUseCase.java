package com.booksms.store.application.usecase.Book.FindUseCase;

import com.booksms.store.application.model.BooksSearchCriteria;
import com.booksms.store.application.usecase.BaseUseCase;
import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.core.domain.repository.IBookRepository;
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
