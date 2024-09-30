package com.booksms.store.interfaceLayer.service.book.impl;

import com.booksms.store.application.model.BookModel;
import com.booksms.store.application.usecase.Book.UpdateUseCase.UpdateBookUseCase;
import com.booksms.store.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.store.interfaceLayer.DTO.Request.OrderType;
import com.booksms.store.interfaceLayer.DTO.Request.SellProductDTO;
import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.interfaceLayer.service.book.IUpdateBookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UpdateBookService implements IUpdateBookService {
    private final UpdateBookUseCase updateBookUseCase;
    private final ModelMapper modelMapper;

    @Override
    public Book updateById(int id, BookRequestDTO request) throws IOException {
        request.setId(id);
        return updateBookUseCase.execute(modelMapper.map(request,BookModel.class));
    }

    @Override
    public Book updateQuantityById(int id, SellProductDTO request) throws IOException {
        BookModel bookDTO = BookModel.builder()
                .id(request.getId())
                .availableQuantity(request.getQuantity())
                .orderType(OrderType.SELL)
                .build();
        return updateBookUseCase.execute(bookDTO);
    }

}
