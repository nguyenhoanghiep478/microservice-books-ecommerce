package com.booksms.book.application.usecase.Book.CreateHandlerUseCase;

import com.booksms.book.application.usecase.Book.CreateUseCase.CreateBookUseCase;
import com.booksms.book.application.usecase.Book.UpdateUseCase.UpdateBookUseCase;
import com.booksms.book.core.domain.entity.Book;
import com.booksms.book.core.domain.entity.Category;
import com.booksms.book.core.domain.exception.MissingArgumentException;
import com.booksms.book.core.domain.repository.IBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateBookHandlerUseCaseTest {
    @Mock
    IBookRepository bookRepository;
    @InjectMocks
    CreateBookHandlerUseCase createBookHandlerUseCase;
    @Mock
    CreateBookUseCase createBookUseCase;
    @Mock
    UpdateBookUseCase updateBookUseCase;

    private Book existedBook;
    private Book newBook;

    @BeforeEach
    void setUp() {
        Category category = new Category();
        category.setId(1);
        category.setName("Book");
        existedBook = new Book();
        existedBook.setTitle("Exited");
        existedBook.setId(1);
        existedBook.setName("Exited");
        existedBook.setAvailableQuantity(5);
        existedBook.setPrice(BigDecimal.valueOf(50));
        existedBook.setCategory(category);

        newBook = new Book();
        newBook.setTitle("New");
        newBook.setId(2);
        newBook.setName("New");
        newBook.setAvailableQuantity(10);
        newBook.setPrice(BigDecimal.valueOf(50));
        newBook.setCategory(category);

    }
    @Test
    void giveExistedBook_whenExecute_thenUpdateQuantity(){
        when(bookRepository.findByName(anyString())).thenReturn(Optional.of(existedBook));
        when(updateBookUseCase.execute(any(Book.class))).thenReturn(existedBook);

        Book result = createBookHandlerUseCase.execute(newBook);

        assertNotNull(result);
        assertEquals(15, result.getAvailableQuantity());

        verify(bookRepository, times(1)).findByName(anyString());
        verify(updateBookUseCase, times(1)).execute(any(Book.class));
        verify(createBookUseCase, times(0)).execute(any(Book.class));
    }
    @Test
    void giveNewBook_whenExecute_thenCreateNewBook(){
        when(bookRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(createBookUseCase.execute(any(Book.class))).thenReturn(newBook);

        Book result = createBookHandlerUseCase.execute(newBook);

        assertNotNull(result);
        assertEquals(10, result.getAvailableQuantity());
        assertEquals(BigDecimal.valueOf(50), result.getPrice());
        assertEquals(newBook.getId(), result.getId());

        verify(bookRepository, times(1)).findByName(anyString());
        verify(updateBookUseCase,times(0)).execute(any(Book.class));
        verify(createBookUseCase, times(1)).execute(any(Book.class));
    }
    @Test
    void giveNewBookWithoutCategory_whenExecute_thenThrowMissingArgumentException(){
        when(bookRepository.findByName(anyString())).thenReturn(Optional.empty());
        newBook.setCategory(null);
        assertThrows(MissingArgumentException.class,
                () -> createBookHandlerUseCase.execute(newBook)
        );
        verify(bookRepository, times(1)).findByName(anyString());
        verify(updateBookUseCase,times(0)).execute(any(Book.class));
        verify(createBookUseCase,times(0)).execute(any(Book.class));
    }
}