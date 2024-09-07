package com.booksms.store.application.usecase.Category;

import com.booksms.store.application.model.CategoriesSearchCriteria;
import com.booksms.store.application.usecase.BaseUseCase;
import com.booksms.store.core.domain.entity.Category;
import com.booksms.store.core.domain.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class FindCategoriesUseCase implements BaseUseCase<List<Category>, CategoriesSearchCriteria> {
    private final ICategoryRepository categoryRepository;
    @Override
    public List<Category> execute(CategoriesSearchCriteria categoriesSearchCriteria) {
        if(categoriesSearchCriteria == null) {
            return categoryRepository.findAll();
        }
        return List.of();
    }
}
