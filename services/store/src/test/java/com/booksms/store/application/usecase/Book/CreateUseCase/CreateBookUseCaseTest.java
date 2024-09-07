package com.booksms.store.application.usecase.Book.CreateUseCase;

import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.core.domain.entity.Category;
import com.booksms.store.core.domain.exception.CreateFailureException;
import com.booksms.store.core.domain.exception.MissingArgumentException;
import com.booksms.store.core.domain.repository.IBookRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateBookUseCaseTest {
    @Mock
    private IBookRepository bookRepository;
    @InjectMocks
    private CreateBookUseCase createBookUseCase;
    private Book validBook;
    private Book withoutCategoryBook;

    @BeforeEach
    void setUp(){
        Category category = new Category();
        category.setId(1);
        validBook = new Book();
        validBook.setName("Book");
        validBook.setImage("Image");
        validBook.setChapter(1);
        validBook.setTitle("Title");
        validBook.setPrice(BigDecimal.valueOf(12.5));
        validBook.setIsInStock(true);
        validBook.setDistributorId(1);
        validBook.setCategory(category);
        validBook.setAvailableQuantity(50);

        withoutCategoryBook = new Book();
        withoutCategoryBook.setId(1);
        withoutCategoryBook.setName("Book");
        withoutCategoryBook.setImage("Image");
        withoutCategoryBook.setChapter(1);
        withoutCategoryBook.setTitle("Title");
        withoutCategoryBook.setPrice(BigDecimal.valueOf(12.5));
        withoutCategoryBook.setIsInStock(true);
        withoutCategoryBook.setAvailableQuantity(50);
    }

    @Test
    void giveValidBook_WhenExecute_thenBookIsSaved() {
        when(bookRepository.save(any(Book.class))).thenReturn(Optional.of(validBook));

        Book savedBook = createBookUseCase.execute(validBook);

        assertNotNull(savedBook);
        assertEquals("Book", savedBook.getName());
        assertEquals("Image", savedBook.getImage());
        assertEquals(BigDecimal.valueOf(12.5), savedBook.getPrice());
        assertEquals(true, savedBook.getIsInStock());
        assertEquals(1, savedBook.getDistributorId());
        assertEquals(1, savedBook.getCategory().getId());
        assertEquals(50, savedBook.getAvailableQuantity());

        verify(bookRepository,times(1)).save(any(Book.class));
    }
    @Test
    void giveBookWithoutCategory_WhenExecute_thenBookIsNotSavedAndThrowMissingArgumentException() {
        assertThrows(MissingArgumentException.class, () -> createBookUseCase.execute(withoutCategoryBook));
        verify(bookRepository,times(0)).save(any(Book.class));
    }

    @Test
    void giveInvalidBook_WhenExecute_thenThrowsCreateFailedExceptionAndReturnEmpty() {
        when(bookRepository.save(any(Book.class))).thenReturn(Optional.empty());
        CreateFailureException exception = assertThrows(CreateFailureException.class,
                () -> createBookUseCase.execute(validBook)
        );
        assertEquals("Book could not be created: Book", exception.getMessage());

        verify(bookRepository,times(1)).save(any(Book.class));
    }
}