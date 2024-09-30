package com.booksms.store.application.usecase.Book.UpdateUseCase;

import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.core.domain.exception.UpdateFailureException;
import com.booksms.store.core.domain.repository.IBookRepository;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class BookStartSellUseCase {

    private final IBookRepository bookRepository;
    BigDecimal value = new BigDecimal("0.00");

    public Book execute(Book book){
        if(book.getPrice().compareTo(value) <= 0){
            throw new BadRequestException("Price to sell must be greater than 0");
        }
        book.setIsInStock(true);
        return bookRepository.save(book).orElseThrow(
                () -> new UpdateFailureException("Start Sell book failed")
        );
    }
}
