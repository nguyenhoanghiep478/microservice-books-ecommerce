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
public class FindBooksUseCase implements BaseUseCase<List<Book>, BooksSearchCriteria> {
    private final IBookRepository bookRepository;
    @Override
    public List<Book> execute(BooksSearchCriteria bookSearchCriteria) {
        List<Book> result = null;
        boolean isInStock = false;
        if (bookSearchCriteria != null) {
            if (bookSearchCriteria.getCategoryId() != null && bookSearchCriteria.getDistributorId() != null && bookSearchCriteria.getCreatedDate() != null) {
                result = bookRepository.findAllLikeNameAndCategoryIdAndDistributorIdAndAfterCreatedDate(bookSearchCriteria.getName(),bookSearchCriteria.getCategoryId(),bookSearchCriteria.getDistributorId(),bookSearchCriteria.getCreatedDate());
            }
            else if (bookSearchCriteria.getCategoryId() != null && bookSearchCriteria.getDistributorId() != null) {
                result = bookRepository.findAllByCategoryIdAndDistributorId(bookSearchCriteria.getCategoryId(),bookSearchCriteria.getDistributorId());
            }
            else if(bookSearchCriteria.getName() != null && bookSearchCriteria.getCategoryId() != null) {
                result = bookRepository.findAllLikeNameAndCategoryId(bookSearchCriteria.getName(),bookSearchCriteria.getCategoryId());
            }
            else if (bookSearchCriteria.getCategoryId() != null && bookSearchCriteria.getCreatedDate() != null) {
                result = bookRepository.findAllByCategoryIdAndCreatedDate(bookSearchCriteria.getCategoryId(),bookSearchCriteria.getCreatedDate());
            }
            else if (bookSearchCriteria.getDistributorId() != null && bookSearchCriteria.getCreatedDate() != null) {
                result = bookRepository.findALlByDistributorIdAndCreatedDate(bookSearchCriteria.getDistributorId(),bookSearchCriteria.getCreatedDate());
            }
            if(bookSearchCriteria.getIsInStock() != null){
               isInStock = bookSearchCriteria.getIsInStock();
            }
            if(bookSearchCriteria.getPageable() != null && result == null){
                result = bookRepository.findAll(bookSearchCriteria.getPageable()).getContent();
            }
        }
            if(result == null){
               result = bookRepository.findAll();
            }
            if(result == null){
                throw new BookNotFoundException(String.format("No books found for search criteria: %s", bookSearchCriteria));
            }
            if(isInStock){
                return result.stream().filter(Book::getIsInStock).toList();
            }

            return result;

    }
}
