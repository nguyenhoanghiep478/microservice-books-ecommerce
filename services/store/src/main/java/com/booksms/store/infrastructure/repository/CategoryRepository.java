package com.booksms.store.infrastructure.repository;

import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.core.domain.entity.Category;
import com.booksms.store.core.domain.repository.ICategoryRepository;
import com.booksms.store.infrastructure.JpaRepository.CategoryJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepository extends AbstractRepository<Category> implements ICategoryRepository{
    private final CategoryJpaRepository categoryJpaRepository;
    CategoryRepository(Class<Category> entityClass, com.booksms.store.infrastructure.JpaRepository.CategoryJpaRepository categoryJpaRepository, CategoryJpaRepository categoryJpaRepository1) {
        super(entityClass);
        this.categoryJpaRepository = categoryJpaRepository1;
    }

    @Override
    public List<Category> findAll() {
        return categoryJpaRepository.findAll();
    }

    @Override
    public List<Category> findBy(List<SearchCriteria> searchCriteria) {
        return abstractSearch(searchCriteria);
    }

    @Override
    public Optional<Category> save(Category category) {
        return Optional.of(categoryJpaRepository.save(category));
    }
}
