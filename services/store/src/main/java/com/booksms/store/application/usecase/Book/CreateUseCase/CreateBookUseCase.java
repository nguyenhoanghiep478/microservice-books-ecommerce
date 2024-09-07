package com.booksms.store.application.usecase.Book.CreateUseCase;

import com.booksms.store.application.usecase.BaseUseCase;
import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.core.domain.exception.CreateFailureException;
import com.booksms.store.core.domain.exception.MissingArgumentException;
import com.booksms.store.core.domain.repository.IBookRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateBookUseCase implements BaseUseCase<Book, Book>{
    private final IBookRepository bookRepository;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Book execute(@NotNull Book newBook) {
        if(newBook.getCategory() == null){
            throw new MissingArgumentException("Category is null");
        }
        return bookRepository.save(newBook).orElseThrow(
                () -> new CreateFailureException(String.format("Book could not be created: %s", newBook.getName()))
        );
    }


}
