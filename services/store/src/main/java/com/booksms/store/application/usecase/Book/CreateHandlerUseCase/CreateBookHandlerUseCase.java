package com.booksms.store.application.usecase.Book.CreateHandlerUseCase;

import com.booksms.store.application.model.BookModel;
import com.booksms.store.application.usecase.Book.CreateUseCase.CreateBookUseCase;
import com.booksms.store.application.usecase.Book.UpdateUseCase.UpdateBookUseCase;
import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.core.domain.exception.MissingArgumentException;
import com.booksms.store.core.domain.repository.IBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateBookHandlerUseCase {
    private final CreateBookUseCase createBookUseCase;
    private final UpdateBookUseCase updateBookUseCase;
    private final IBookRepository bookRepository;
    @Transactional(rollbackFor = Exception.class)
    public Book execute(BookModel book) throws IOException {

        if(book.getName() != null){
            Optional<Book> existingBook = bookRepository.findByName(book.getName());
            if(existingBook.isPresent()){
                return updateBookUseCase.execute(book);
            }
        }
        if(book.getCategoryId() == null){
            throw new MissingArgumentException("missing category");
        }
        return createBookUseCase.execute(book);
    }
}
