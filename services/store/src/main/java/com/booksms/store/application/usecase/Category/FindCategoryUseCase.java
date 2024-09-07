package com.booksms.book.application.usecase.Category;

import com.booksms.book.application.model.CategorySearchCriteria;
import com.booksms.book.application.usecase.BaseUseCase;
import com.booksms.book.core.domain.entity.Category;
import com.booksms.book.core.domain.exception.CategoryNotFoundException;
import com.booksms.book.core.domain.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindCategoryUseCase implements BaseUseCase<Category, CategorySearchCriteria> {
    private final ICategoryRepository categoryRepository;

    @Override
    public Category execute(CategorySearchCriteria categorySearchCriteria) {
        if(categorySearchCriteria != null){
            if(categorySearchCriteria.getName() != null && categorySearchCriteria.getBooksId() != null){
                return categoryRepository.findByNameAndBookId(categorySearchCriteria.getName(),categorySearchCriteria.getBooksId()).orElseThrow(
                        () -> new CategoryNotFoundException(String.format("Category with name %s and book Id %s not found", categorySearchCriteria.getName(),categorySearchCriteria.getBooksId()))
                );
            }else if(categorySearchCriteria.getName() != null ){
                return categoryRepository.findByName(categorySearchCriteria.getName()).orElseThrow(
                        () -> new CategoryNotFoundException(String.format("Category with name %s not found", categorySearchCriteria.getName()))
                );
            }else if(categorySearchCriteria.getBooksId() != null){
                return categoryRepository.findByBookId(categorySearchCriteria.getBooksId()).orElseThrow(
                        () -> new CategoryNotFoundException(String.format("Category with book id %s not found", categorySearchCriteria.getBooksId()))
                );
            }else if(categorySearchCriteria.getId() != null){
                return categoryRepository.findById(categorySearchCriteria.getId().intValue()).orElseThrow(
                        () -> new CategoryNotFoundException(String.format("Category with id %s not found", categorySearchCriteria.getId()))
                );
            }
        }
        return null;
    }
}
