package com.booksms.store.interfaceLayer.service.category.impl;

import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.application.usecase.category.FindCategoryUseCase;
import com.booksms.store.core.domain.entity.Category;
import com.booksms.store.interfaceLayer.service.category.IFindCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindCategoryService implements IFindCategoryService {
    private final FindCategoryUseCase findCategoryUseCase;
    @Override
    public List<Category> findAll() {
        return this.findCategoryUseCase.execute(null);
    }

    public Category findById(int id) {
        return this.findCategoryUseCase.execute(
               List.of(SearchCriteria.builder()
                               .key("id")
                               .operation(":")
                               .value(id)
                       .build())
        ).get(0);
    }
    @Override
    public Category findByName(String name) {
        return this.findCategoryUseCase.execute(
                List.of(SearchCriteria.builder()
                        .key("name")
                        .operation(":")
                        .value(name)
                        .build())
        ).get(0);
    }
    @Override
    public boolean isExistedByName(String name) {
        return this.findByName(name) != null;
    }
    @Override
    public boolean isExistedById(int id) {
        return this.findById(id) != null;
    }


}