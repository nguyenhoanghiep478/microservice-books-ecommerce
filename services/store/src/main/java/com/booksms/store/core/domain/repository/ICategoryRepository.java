package com.booksms.store.core.domain.repository;

import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.core.domain.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryRepository {
    List<Category> findAll();

    List<Category> findBy(List<SearchCriteria> searchCriteria);

    Optional<Category> save(Category category);
}
