package com.booksms.store.interfaceLayer.service.category.impl;

import com.booksms.store.application.usecase.Category.CreateCategoryUseCase;
import com.booksms.store.core.domain.entity.Category;
import com.booksms.store.interfaceLayer.DTO.Request.CategoryDTO;
import com.booksms.store.interfaceLayer.service.category.ICreateCategoryService;
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
