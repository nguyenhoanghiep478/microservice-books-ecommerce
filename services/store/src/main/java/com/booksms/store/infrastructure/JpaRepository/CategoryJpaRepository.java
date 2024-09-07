package com.booksms.store.infrastructure.JpaRepository;

import com.booksms.store.core.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryJpaRepository extends JpaRepository<Category, Integer> {
    @Query("select b from Book b join b.category c where c.name = :categoryName and b.id = :bookId")
    Optional<Category> findCategoryByNameAndBookId(@Param("categoryName") String name,@Param("bookId") Integer booksId);
    Optional<Category> findOneByName(String name);
    Optional<Category> findOneById(int id);
}
