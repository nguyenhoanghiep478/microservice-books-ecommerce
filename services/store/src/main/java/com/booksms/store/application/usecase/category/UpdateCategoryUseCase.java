package com.booksms.store.application.usecase.category;

import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.application.usecase.BaseUseCase;
import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.core.domain.entity.Category;
import com.booksms.store.core.domain.exception.CategoryNotFoundException;
import com.booksms.store.core.domain.exception.UpdateFailureException;
import com.booksms.store.core.domain.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateCategoryUseCase implements BaseUseCase<Category, Category> {
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
        if (existed == null) {
            throw new CategoryNotFoundException(String.format("Category with name %s not found", category.getName()));
        } else {
            return this.categoryRepository.save(this.mergeCategory(existed, category)).orElseThrow(() -> {
                return new UpdateFailureException("update category failed");
            });
        }
    }

    private Category mergeCategory(Category existed, Category category) {
        if (category.getName() != null) {
            existed.setName(category.getName());
        }

        if (category.getDescription() != null) {
            existed.setDescription(existed.getDescription().trim());
        }

        if (category.getBooks() != null) {
            Iterator var3 = category.getBooks().iterator();

            while(var3.hasNext()) {
                Book book = (Book)var3.next();
                existed.getBooks().add(book);
            }
        }

        return existed;
    }


}