package com.booksms.store.config;

import com.booksms.store.core.domain.entity.*;
import com.booksms.store.infrastructure.JpaRepository.BookJpaRepository;
import com.booksms.store.infrastructure.JpaRepository.InventoryJpaRepository;
import com.booksms.store.infrastructure.repository.AbstractRepository;
import com.booksms.store.infrastructure.repository.BookRepository;
import com.booksms.store.infrastructure.repository.InventoryRepository;
import com.booksms.store.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.store.interfaceLayer.DTO.Response.BookResponseDTO;
import com.booksms.store.interfaceLayer.DTO.Response.InventoryDTO;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
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
        modelMapper.typeMap(BookRequestDTO.class,Book.class).addMappings(mapper ->{
           mapper.map(BookRequestDTO::getInventoryId,Book::setInventoryBooks); 
        });
        return modelMapper;
    }

    @Bean
    @Qualifier("bookClass")
    public Class<Book> bookClass() {
        return Book.class;
    }

    @Bean
    @Qualifier("inventoryClass")
    public Class<Inventory> inventoryClass() {
        return Inventory.class;
    }

    @Bean
    public Class<Address> addressClass() {
        return Address.class;
    }

    @Bean
    public Class<Category> categoryClass() {
        return Category.class;
    }
}
