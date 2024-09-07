package com.booksms.book.infrastructure.repository;

import com.booksms.book.core.domain.entity.Category;
import com.booksms.book.core.domain.repository.ICategoryRepository;
import com.booksms.book.infrastructure.JpaRepository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class CategoryRepository implements ICategoryRepository {
    private final CategoryJpaRepository categoryJpaRepository;
    @Override
    public Optional<Category> findById(int categoryId) {
        return categoryJpaRepository.findOneById(categoryId);
    }

    @Override
    public List<Category> findAll() {
        return categoryJpaRepository.findAll();
    }

    @Override
    public Optional<Category> findByNameAndBookId(String name, Integer booksId) {
        return categoryJpaRepository.findCategoryByNameAndBookId(name,booksId);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryJpaRepository.findOneByName(name);
    }

    @Override
    public Optional<Category> findByBookId(Integer booksId) {
        return Optional.empty();
    }

    @Override
    public Optional<Category> save(Category category) {
        return Optional.of(categoryJpaRepository.save(category));
    }
}
