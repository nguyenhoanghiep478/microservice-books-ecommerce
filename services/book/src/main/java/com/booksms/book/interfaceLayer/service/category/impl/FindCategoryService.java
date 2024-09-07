package com.booksms.book.interfaceLayer.service.category.impl;

import com.booksms.book.application.model.CategorySearchCriteria;
import com.booksms.book.application.usecase.Category.FindCategoriesUseCase;
import com.booksms.book.application.usecase.Category.FindCategoryUseCase;
import com.booksms.book.core.domain.entity.Category;
import com.booksms.book.interfaceLayer.service.category.IFindCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindCategoryService implements IFindCategoryService {
    private final FindCategoryUseCase findCategoryUseCase;
    private final FindCategoriesUseCase findCategoriesUseCase;

    @Override
    public List<Category> findAll() {
        return findCategoriesUseCase.execute(null);
    }

    @Override
    public Category findById(int id) {
        return findCategoryUseCase.execute(CategorySearchCriteria.builder()
                .id(id)
                .build());
    }

    @Override
    public Category findByName(String name) {
        return findCategoryUseCase.execute(CategorySearchCriteria.builder()
                .name(name)
                .build());
    }
    @Override
    public boolean isExistedByName(String name) {
       return findByName(name) != null;
    }
    @Override
    public boolean isExistedById(int id) {
     return findById(id) != null;
    }
}
