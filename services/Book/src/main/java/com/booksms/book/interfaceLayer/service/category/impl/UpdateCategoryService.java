package com.booksms.book.interfaceLayer.service.category.impl;

import com.booksms.book.application.usecase.Category.UpdateCategoryUseCase;
import com.booksms.book.core.domain.entity.Category;
import com.booksms.book.interfaceLayer.DTO.Request.CategoryDTO;
import com.booksms.book.interfaceLayer.service.category.IUpdateCategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCategoryService implements IUpdateCategoryService {
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final ModelMapper modelMapper;


    @Override
    public Category updateById(int id, CategoryDTO request) {
        Category category = modelMapper.map(request, Category.class);
        return updateCategoryUseCase.execute(category);
    }
}
