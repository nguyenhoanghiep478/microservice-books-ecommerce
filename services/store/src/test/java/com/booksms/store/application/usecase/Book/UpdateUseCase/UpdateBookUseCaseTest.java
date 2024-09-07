package com.booksms.book.application.usecase.Book.UpdateUseCase;

import com.booksms.book.application.model.CategorySearchCriteria;
import com.booksms.book.application.usecase.Book.FindUseCase.FindBookUseCase;
import com.booksms.book.application.usecase.Category.FindCategoryUseCase;
import com.booksms.book.core.domain.entity.Book;
import com.booksms.book.core.domain.entity.Category;
import com.booksms.book.core.domain.exception.BookNotFoundException;
import com.booksms.book.core.domain.exception.CategoryNotFoundException;
import com.booksms.book.core.domain.exception.UpdateFailureException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateBookUseCaseTest {
    @Mock
    private IBookRepository bookRepository;
    @InjectMocks
    private UpdateBookUseCase updateBookUseCase;
    @Mock
    private FindBookUseCase findBookUseCase;
    @Mock
    private FindCategoryUseCase findCategoryUseCase;
    private Book existedBook;
    private Book updatedBook;
    @BeforeEach
    void setUp(){
        Category category = new Category();
        category.setId(1);
        category.setName("action");

        existedBook = new Book();
        existedBook.setId(1);
        existedBook.setCategory(category);
        existedBook.setName("Book");
        existedBook.setPrice(BigDecimal.valueOf(10));

        updatedBook = new Book();
        updatedBook.setName("updatedBook");
        updatedBook.setCategory(category);
        updatedBook.setPrice(BigDecimal.valueOf(20));
    }
    @Test
    void testUpdateBook_whenExecute_ThenBookIsUpdated(){
        when(bookRepository.findOneByName(anyString())).thenReturn(Optional.of(existedBook));
        when(bookRepository.save(any(Book.class))).thenReturn(Optional.of(existedBook));

        Book result = updateBookUseCase.execute(updatedBook);
        assertNotNull(result);
        assertEquals(updatedBook.getName(), result.getName());
        assertEquals(updatedBook.getPrice(), result.getPrice());
        assertEquals(updatedBook.getCategory(), result.getCategory());
        verify(bookRepository,times(1)).findOneByName(anyString());
        verify(bookRepository,times(1)).save(any(Book.class));
    }
    @Test
    void testBookNotFoundException_whenExecute_ThenBookIsNotUpdated(){
        when(bookRepository.findOneByName(anyString())).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> updateBookUseCase.execute(updatedBook));

        verify(bookRepository,times(1)).findOneByName(anyString());
        verify(findCategoryUseCase,times(0)).execute(any(CategorySearchCriteria.class));
        verify(bookRepository,times(0)).save(any(Book.class));
    }
    @Test
    void testWithoutCategoryBook_whenExecute_thenThrowCategoryNotFoundException(){
        updatedBook.setCategory(new Category());
        updatedBook.getCategory().setId(2);
        when(bookRepository.findOneByName(anyString())).thenReturn(Optional.of(existedBook));
        when(findCategoryUseCase.execute(any(CategorySearchCriteria.class))).thenThrow(new CategoryNotFoundException("Category not found"));

        assertThrows(CategoryNotFoundException.class,
                () -> updateBookUseCase.execute(updatedBook)
        );
        verify(bookRepository,times(1)).findOneByName(anyString());
        verify(findCategoryUseCase,times(1)).execute(any(CategorySearchCriteria.class));
        verify(bookRepository,times(0)).save(any(Book.class));
    }

    @Test
    void testUpdateBook_whenExecuteFailed_thenThrowsUpdateBookFailureException(){
        when(bookRepository.findOneByName(anyString())).thenReturn(Optional.of(existedBook));
        when(bookRepository.save(any(Book.class))).thenReturn(Optional.empty());

        assertThrows(UpdateFailureException.class,()->
                updateBookUseCase.execute(updatedBook)
        );
        verify(bookRepository,times(1)).findOneByName(anyString());
        verify(bookRepository,times(1)).save(any(Book.class));
    }
}