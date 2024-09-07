package com.booksms.book.application.usecase.Category;

import com.booksms.book.application.model.CategorySearchCriteria;
import com.booksms.book.application.usecase.BaseUseCase;
import com.booksms.book.core.domain.entity.Book;
import com.booksms.book.core.domain.entity.Category;
import com.booksms.book.core.domain.exception.CategoryNotFoundException;
import com.booksms.book.core.domain.exception.UpdateFailureException;
import com.booksms.book.core.domain.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateCategoryUseCase implements BaseUseCase<Category,Category> {
    private final ICategoryRepository categoryRepository;
    private final FindCategoryUseCase findCategoryUseCase;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Category execute(Category category) {
        Category existed = findCategoryUseCase.execute(CategorySearchCriteria.builder()
                        .name(category.getName())
                .build());
        if(existed == null) {
            throw new CategoryNotFoundException(String.format("Category with name %s not found", category.getName()));
        }
        return categoryRepository.save(mergeCategory(existed,category)).orElseThrow(
                () -> new UpdateFailureException("update category failed")
        );
    }

    private Category mergeCategory(Category existed, Category category) {
        if(category.getName() != null){
            existed.setName(category.getName());
        }
        if(category.getDescription() != null){
            existed.setDescription(existed.getDescription().trim());
        }
        if(category.getBooks() != null){
            for(Book book : category.getBooks()){
                existed.getBooks().add(book);
            }
        }
        return existed;
    }
}