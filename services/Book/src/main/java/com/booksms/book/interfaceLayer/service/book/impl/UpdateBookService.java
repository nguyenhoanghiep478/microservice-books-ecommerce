package com.booksms.book.interfaceLayer.service.book.impl;

import com.booksms.book.application.model.BookModel;
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
    public Book updateQuantityById(int id, UpdateQuantityDTO request) throws IOException {
        BookModel bookDTO = BookModel.builder()
                .id(request.getId())
                .availableQuantity(request.getQuantity())
                .orderType(request.getType())
                .build();
        return updateBookUseCase.execute(bookDTO);
    }

}
