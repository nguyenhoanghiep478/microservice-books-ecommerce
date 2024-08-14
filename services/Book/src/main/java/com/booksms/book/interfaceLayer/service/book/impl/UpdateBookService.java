package com.booksms.book.interfaceLayer.service.book.impl;

import com.booksms.book.application.usecase.Book.UpdateUseCase.UpdateBookUseCase;
import com.booksms.book.core.domain.exception.InSufficientQuantityException;
import com.booksms.book.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.book.interfaceLayer.DTO.Request.OrderType;
import com.booksms.book.interfaceLayer.DTO.Request.UpdateQuantityDTO;
import com.booksms.book.core.domain.entity.Book;
import com.booksms.book.core.domain.entity.Category;
import com.booksms.book.interfaceLayer.service.book.IFindBookService;
import com.booksms.book.interfaceLayer.service.book.IUpdateBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateBookService implements IUpdateBookService {
    private final IFindBookService findBookService;
    private final UpdateBookUseCase updateBookUseCase;
    @Override
    public Book updateById(int id, BookRequestDTO request) {
        Book book = findBookService.findById(id);
        if(request.getCategoryId() != null) {
            Category category = new Category();
            category.setId(request.getCategoryId());
            book.setCategory(category);
        }
        return updateBookUseCase.execute(book);
    }

    @Override
    public Book updateQuantityById(int id, UpdateQuantityDTO request) {
        Book book = findBookService.findById(id);
        if(request.getType() == OrderType.SELL){
            if(book.getAvailableQuantity() < request.getQuantity()) {
                throw new InSufficientQuantityException("not enough available quantity");
            }
            book.setAvailableQuantity(book.getAvailableQuantity() - request.getQuantity());
        }else{
            book.setAvailableQuantity(book.getAvailableQuantity() + request.getQuantity());
        }
        return updateBookUseCase.execute(book);
    }

}
