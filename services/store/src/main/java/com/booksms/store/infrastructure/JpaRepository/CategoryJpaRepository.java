package com.booksms.store.infrastructure.JpaRepository;

import com.booksms.store.core.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Integer> {
}
