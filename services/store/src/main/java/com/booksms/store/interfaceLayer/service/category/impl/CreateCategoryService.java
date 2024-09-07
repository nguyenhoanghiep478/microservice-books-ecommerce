package com.booksms.book.interfaceLayer.service.category.impl;

import com.booksms.book.application.usecase.Category.CreateCategoryUseCase;
import com.booksms.book.core.domain.entity.Category;
import com.booksms.book.interfaceLayer.DTO.Request.CategoryDTO;
import com.booksms.book.interfaceLayer.service.category.ICreateCategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCategoryService implements ICreateCategoryService {
    private final CreateCategoryUseCase createCategoryUseCase;
    private final ModelMapper modelMapper;

    @Override
    public Category insertCategory(CategoryDTO categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        return createCategoryUseCase.execute(category);
    }
}
