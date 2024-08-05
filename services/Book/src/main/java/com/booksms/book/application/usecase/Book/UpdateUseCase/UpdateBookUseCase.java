package com.booksms.book.application.usecase.Book.UpdateUseCase;

import com.booksms.book.application.model.CategorySearchCriteria;
import com.booksms.book.application.usecase.BaseUseCase;
import com.booksms.book.application.usecase.Book.FindUseCase.FindBookUseCase;
import com.booksms.book.application.usecase.Category.FindCategoryUseCase;
import com.booksms.book.core.domain.entity.Book;
import com.booksms.book.core.domain.entity.Category;
import com.booksms.book.core.domain.exception.BookNotFoundException;
import com.booksms.book.core.domain.exception.CategoryNotFoundException;
import com.booksms.book.core.domain.exception.UpdateFailureException;
import com.booksms.book.core.domain.repository.IBookRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateBookUseCase implements BaseUseCase<Book, Book> {
    private final IBookRepository bookRepository;
    private final FindCategoryUseCase findCategoryUseCase;
    @Override
    @Transactional(rollbackFor = BookNotFoundException.class)
    public Book execute(@NotNull Book newBook) {
        //check book
        Book oldBook = bookRepository.findOneByName(newBook.getName()).orElseThrow(
                () -> new BookNotFoundException(String.format("Book with name '%s' not found", newBook.getName()))
        );
        //check category
        if(newBook.getCategory()!= null){
            if(newBook.getCategory().getName() == null){
                Category newCategory = findCategoryUseCase.execute(CategorySearchCriteria.builder()
                        .id(newBook.getCategory().getId())
                        .build());
                newBook.setCategory(newCategory);
            }
            newBook.setCategory(newBook.getCategory());
        }
        return bookRepository.save(mergeEntity(oldBook, newBook)).orElseThrow(
                () -> new UpdateFailureException(String.format("Book with name '%s' update failed", newBook.getName()))
        );
    }
    private Book mergeEntity(Book oldBook,Book newBook) {
            if(newBook.getPrice() != null){
                oldBook.setPrice(newBook.getPrice());
            }
            if(newBook.getName() != null){
                oldBook.setName(newBook.getName());
            }
            if(newBook.getTitle() != null){
                oldBook.setTitle(newBook.getTitle());
            }
            if(newBook.getCategory() != null){
                oldBook.setCategory(newBook.getCategory());
            }
            if(newBook.getDistributorId() != null){
                oldBook.setDistributorId(newBook.getDistributorId());
            }
            if(newBook.getImage() != null){
                oldBook.setImage(newBook.getImage());
            }
            if(newBook.getName() != null){
                oldBook.setName(newBook.getName());
            }
            if(newBook.getAvailableQuantity() != null){
                oldBook.setAvailableQuantity(newBook.getAvailableQuantity());
            }
        return oldBook;
    }
}
