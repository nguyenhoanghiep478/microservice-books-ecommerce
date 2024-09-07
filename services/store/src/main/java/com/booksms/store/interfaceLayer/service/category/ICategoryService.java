package com.booksms.store.interfaceLayer.service.category;

import com.booksms.store.interfaceLayer.DTO.Request.CategoryDTO;

import java.util.List;

public interface ICategoryService {

    CategoryDTO insert(CategoryDTO request);

    CategoryDTO updateById(int id, CategoryDTO request);

    CategoryDTO deleteById(int id);

    List<CategoryDTO> findAll();

    CategoryDTO findById(int id);

    CategoryDTO findByName(String name);
}
