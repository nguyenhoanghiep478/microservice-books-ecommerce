

package com.booksms.store.application.usecase.category;

import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.application.usecase.BaseUseCase;
import com.booksms.store.core.domain.entity.Category;
import com.booksms.store.core.domain.exception.CategoryExistException;
import com.booksms.store.core.domain.exception.CreateFailureException;
import com.booksms.store.core.domain.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateCategoryUseCase implements BaseUseCase<Category, Category> {
    private final ICategoryRepository categoryRepository;
    private final FindCategoryUseCase findCategoryUseCase;

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public Category execute(Category category) {
        Category existed = this.findCategoryUseCase.execute(List.of(SearchCriteria.builder()
                        .key("name")
                        .operation(":")
                        .value(category.getName())
                .build())).get(0);
        if (existed != null) {
            throw new CategoryExistException(String.format("Category with name %s existed", category.getName()));
        } else {
            return this.categoryRepository.save(category).orElseThrow(() -> {
                return new CreateFailureException("create category failed");
            });
        }
    }


}