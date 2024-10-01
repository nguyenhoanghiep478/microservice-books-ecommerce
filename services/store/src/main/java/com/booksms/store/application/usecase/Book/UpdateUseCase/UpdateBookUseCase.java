package com.booksms.store.application.usecase.Book.UpdateUseCase;

import com.booksms.store.application.model.BookModel;
import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.application.usecase.category.FindCategoryUseCase;
import com.booksms.store.application.usecase.state.UtilsUseCase;
import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.core.domain.entity.Category;
import com.booksms.store.core.domain.exception.BookException.BookNotFoundException;
import com.booksms.store.core.domain.exception.InSufficientQuantityException;
import com.booksms.store.core.domain.exception.UpdateFailureException;
import com.booksms.store.core.domain.repository.IBookRepository;
import com.booksms.store.interfaceLayer.DTO.Request.OrderType;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateBookUseCase {
    private final IBookRepository bookRepository;
    private final FindCategoryUseCase findCategoryUseCase;
    private final UtilsUseCase utilsUseCase;


    @Transactional(rollbackFor = BookNotFoundException.class)
    public Book execute(@NotNull BookModel request) throws IOException {
        //check book
        Book oldBook = bookRepository.findOneByBookId(request.getId()).orElseThrow(
                () -> new BookNotFoundException(String.format("Book with name '%s' not found", request.getName()))
        );

        return bookRepository.save(mergeEntity(oldBook, request)).orElseThrow(
                () -> new UpdateFailureException(String.format("Book with name '%s' update failed", request.getName()))
        );
    }


    private Book mergeEntity(Book oldBook,BookModel newBook) throws IOException {
            if(newBook.getPrice() != null){
                oldBook.setPrice(newBook.getPrice());
            }
            if(newBook.getName() != null){
                oldBook.setName(newBook.getName());
            }
            if(newBook.getTitle() != null){
                oldBook.setTitle(newBook.getTitle());
            }
            if(newBook.getCategoryId() != null){
               Category category = findCategoryUseCase.execute(
                               List.of(SearchCriteria.builder()
                                               .key("id")
                                               .operation(":")
                                               .value(newBook.getCategoryId())
                                       .build())).get(0);
                oldBook.setCategory(category);
            }
            if(newBook.getDistributorId() != null){
                oldBook.setDistributorId(newBook.getDistributorId());
            }
            if(newBook.getImage() != null){
               String lastFileName = utilsUseCase.handleImageToPath(newBook.getImage(), oldBook.getName());
                oldBook.setImage(lastFileName);
            }
            if(newBook.getName() != null){
                oldBook.setName(newBook.getName());
            }
            if(newBook.getAvailableQuantity() != null){
                if(newBook.getOrderType() == OrderType.SELL){
                    if(oldBook.getAvailableQuantity() < newBook.getAvailableQuantity()) {
                        throw new InSufficientQuantityException("not enough available quantity");
                    }
                    oldBook.setAvailableQuantity(oldBook.getAvailableQuantity() - newBook.getAvailableQuantity());
                }else if(newBook.getOrderType() == OrderType.BUY){
                    oldBook.setAvailableQuantity(oldBook.getAvailableQuantity() + newBook.getAvailableQuantity());
                }else{
                    oldBook.setAvailableQuantity(newBook.getAvailableQuantity());
                }
            }
            if(newBook.getIsInStock() != null){
                oldBook.setIsInStock(newBook.getIsInStock());
            }
        return oldBook;
    }


}
