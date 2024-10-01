package com.booksms.store.infrastructure.JpaRepository;

import com.booksms.store.core.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<Book, Integer> {
}
