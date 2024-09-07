package com.booksms.store.config;

import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.core.domain.entity.InventoryBook;
import com.booksms.store.infrastructure.JpaRepository.BookJpaRepository;
import com.booksms.store.infrastructure.repository.BookRepository;
import com.booksms.store.interfaceLayer.DTO.Response.BookResponseDTO;
import com.booksms.store.interfaceLayer.DTO.Response.InventoryDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Book.class, BookResponseDTO.class).addMappings(mapper -> {
            mapper.map(Book::getInventory,BookResponseDTO::setInventory);
        });
        return modelMapper;
    }
    @Bean
    public BookRepository bookRepository(BookJpaRepository bookJpaRepository){
        return new BookRepository(bookJpaRepository);
    }
}
