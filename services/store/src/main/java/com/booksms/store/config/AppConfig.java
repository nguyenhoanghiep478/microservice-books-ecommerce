package com.booksms.book.config;

import com.booksms.book.infrastructure.JpaRepository.BookJpaRepository;
import com.booksms.book.infrastructure.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public BookRepository bookRepository(BookJpaRepository bookJpaRepository){
        return new BookRepository(bookJpaRepository);
    }
}
