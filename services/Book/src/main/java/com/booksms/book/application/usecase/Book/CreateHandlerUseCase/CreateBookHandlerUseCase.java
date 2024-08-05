package com.booksms.book.application.usecase.Book.CreateHandlerUseCase;

import com.booksms.book.application.usecase.BaseUseCase;
import com.booksms.book.application.usecase.Book.CreateUseCase.CreateBookUseCase;
import com.booksms.book.application.usecase.Book.UpdateUseCase.UpdateBookUseCase;
import com.booksms.book.application.usecase.Category.FindCategoryUseCase;
import com.booksms.book.core.domain.entity.Book;
import com.booksms.book.core.domain.exception.MissingArgumentException;
import com.booksms.book.core.domain.repository.IBookRepository;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateBookHandlerUseCase implements BaseUseCase<Book, Book> {
    private final CreateBookUseCase createBookUseCase;
    private final UpdateBookUseCase updateBookUseCase;
    private final IBookRepository bookRepository;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Book execute(Book book) {
        if(book.getName() != null){
            Optional<Book> existingBook = bookRepository.findByName(book.getName());
            if(existingBook.isPresent()){
                Book bookToUpdate = existingBook.get();
                bookToUpdate.setAvailableQuantity(bookToUpdate.getAvailableQuantity() + book.getAvailableQuantity());
                return updateBookUseCase.execute(bookToUpdate);
            }
        }
        if(book.getCategory() == null){
            throw new MissingArgumentException("missing category");
        }
        return createBookUseCase.execute(book);
    }
}
