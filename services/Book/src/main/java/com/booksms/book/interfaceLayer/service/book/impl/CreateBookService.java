package com.booksms.book.interfaceLayer.service.book.impl;

import com.booksms.book.application.usecase.Book.CreateHandlerUseCase.CreateBookHandlerUseCase;
import com.booksms.book.web.mapper.GenericMapper;
import com.booksms.book.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.book.core.domain.entity.Book;
import com.booksms.book.core.domain.entity.Category;
import com.booksms.book.interfaceLayer.service.book.ICreateBookService;
import com.booksms.book.interfaceLayer.service.category.IFindCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateBookService implements ICreateBookService {
    private final IFindCategoryService findCategoryService;
    private final GenericMapper<Book, BookRequestDTO, BookRequestDTO> bookMapper;
    private final CreateBookHandlerUseCase bookCreateHandlerUseCase;
    @Override
    @Transactional
    public Book insert(BookRequestDTO request) {
        Category category = findCategoryService.findById(request.getCategoryId());
        Book book = bookMapper.toEntity(request, Book.class);
        book.setCategory(category);
        return bookCreateHandlerUseCase.execute(book);
    }
}
