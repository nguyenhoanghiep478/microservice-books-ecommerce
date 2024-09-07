package com.booksms.book.application.usecase.Book.FindUseCase;

import com.booksms.book.application.model.BookSearchCriteria;
import com.booksms.book.core.domain.entity.Book;
import com.booksms.book.core.domain.entity.Category;
import com.booksms.book.core.domain.repository.IBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class FindBookUseCaseTest {
    @Mock
    IBookRepository bookRepository;
    @InjectMocks
    FindBookUseCase findBookUseCase;
    private BookSearchCriteria findWithCriteria;
    private Book expectResult;
    @BeforeEach
    void setUp() {
        findWithCriteria = new BookSearchCriteria();
        expectResult = new Book();
    }

    @Test
    void giveSearchCriteriaWithNameAndCategoryId_whenExecute_thenReturnBook(){
        expectResult.setName("A");
        Category category = new Category();
        category.setId(1);
        expectResult.setCategory(category);
        expectResult.setId(1);

        findWithCriteria.setBookId(1);
        findWithCriteria.setCategoryId(1);
        findWithCriteria.setName("A");

        when(bookRepository.findOneByNameAndCategoryId("A",1)).thenReturn(Optional.of(expectResult));
        long startTime = System.currentTimeMillis();
        Book actualResult = findBookUseCase.execute(findWithCriteria);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        assertTrue(duration < 5,duration + "ms");
        assertNotNull(actualResult);
        assertEquals(expectResult.getName(), actualResult.getName());
        assertEquals(expectResult.getId(), actualResult.getId());
        assertEquals(expectResult.getCategory(), actualResult.getCategory());

        verify(bookRepository, times(1)).findOneByNameAndCategoryId("A",1);
        verify(bookRepository, times(0)).findOneByBookIdAndCategoryId(anyInt(),anyInt());
        verify(bookRepository,times(0)).findOneByBookId(anyInt());
        verify(bookRepository,times(0)).findOneByName(anyString());
        verify(bookRepository,times(0)).findOneByCategoryId(anyInt());
    }
    @Test
    void giveSearchCriteriaWithBookIdAndCategoryId_whenExecute_thenReturnBook(){
        Category category = new Category();
        category.setId(1);
        expectResult.setCategory(category);
        expectResult.setId(1);

        findWithCriteria.setBookId(1);
        findWithCriteria.setCategoryId(1);

        when(bookRepository.findOneByBookIdAndCategoryId(1,1)).thenReturn(Optional.of(expectResult));

        Book actualResult = findBookUseCase.execute(findWithCriteria);

        assertNotNull(actualResult);
        assertEquals(expectResult.getId(), actualResult.getId());
        assertEquals(expectResult.getCategory(), actualResult.getCategory());

        verify(bookRepository, times(0)).findOneByNameAndCategoryId("A",1);
        verify(bookRepository, times(1)).findOneByBookIdAndCategoryId(anyInt(),anyInt());
        verify(bookRepository,times(0)).findOneByBookId(anyInt());
        verify(bookRepository,times(0)).findOneByName(anyString());
        verify(bookRepository,times(0)).findOneByCategoryId(anyInt());
    }

    @Test
    void giveSearchCriteriaWithName(){
        expectResult.setName("A");

        findWithCriteria.setName("A");

        when(bookRepository.findOneByName("A")).thenReturn(Optional.of(expectResult));
        long startTime = System.currentTimeMillis();
        Book actualResult = findBookUseCase.execute(findWithCriteria);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        assertNotNull(actualResult);
        assertEquals(expectResult.getName(), actualResult.getName());
        assertTrue(duration < 5, duration + "ms");

        verify(bookRepository, times(0)).findOneByNameAndCategoryId(anyString(),anyInt());
        verify(bookRepository, times(0)).findOneByBookIdAndCategoryId(anyInt(),anyInt());
        verify(bookRepository,times(0)).findOneByBookId(anyInt());
        verify(bookRepository,times(1)).findOneByName(anyString());
        verify(bookRepository,times(0)).findOneByCategoryId(anyInt());
    }
}