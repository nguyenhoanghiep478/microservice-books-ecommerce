package com.booksms.store.application.usecase.category;

import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.core.domain.entity.Category;
import com.booksms.store.core.domain.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindCategoryUseCase {
    private final ICategoryRepository categoryRepository;

    public List<Category> execute(List<SearchCriteria> searchCriteria) {
        if(searchCriteria == null || searchCriteria.isEmpty()) {
            return categoryRepository.findAll();
        }
        return categoryRepository.findBy(searchCriteria);
    }
}
