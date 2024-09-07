package com.booksms.store.application.usecase.Category;

import com.booksms.store.application.model.CategorySearchCriteria;
import com.booksms.store.application.usecase.BaseUseCase;
import com.booksms.store.core.domain.entity.Category;
import com.booksms.store.core.domain.exception.CategoryExistException;
import com.booksms.store.core.domain.exception.CreateFailureException;
import com.booksms.store.core.domain.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateCategoryUseCase implements BaseUseCase<Category, Category> {
    private final ICategoryRepository categoryRepository;
    private final FindCategoryUseCase findCategoryUseCase;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Category execute(Category category) {
        Category existed= findCategoryUseCase.execute(CategorySearchCriteria.builder()
                        .name(category.getName())
                .build());
        if(existed != null){
            throw new CategoryExistException(String.format("Category with name %s existed",category.getName()));
        }
        return categoryRepository.save(category).orElseThrow(
                () -> new CreateFailureException("create category failed")
        );
    }
}
