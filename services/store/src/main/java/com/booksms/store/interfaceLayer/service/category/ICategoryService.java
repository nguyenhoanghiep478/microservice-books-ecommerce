package com.booksms.book.interfaceLayer.service.category;

import com.booksms.book.interfaceLayer.DTO.Request.CategoryDTO;

import java.util.List;

public interface ICategoryService {

    CategoryDTO insert(CategoryDTO request);

    CategoryDTO updateById(int id, CategoryDTO request);

    CategoryDTO deleteById(int id);

    List<CategoryDTO> findAll();

    CategoryDTO findById(int id);

    CategoryDTO findByName(String name);
}
