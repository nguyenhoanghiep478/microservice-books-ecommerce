package com.booksms.store.core.domain.repository;

import com.booksms.store.core.domain.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryRepository {
    Optional<Category> findById(int categoryId);
    List<Category> findAll();

    Optional<Category> findByNameAndBookId(String name, Integer booksId);

    Optional<Category> findByName(String name);

    Optional<Category> findByBookId(Integer booksId);

    Optional<Category> save(Category category);
}
