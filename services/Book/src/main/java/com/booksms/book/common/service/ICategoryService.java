package com.booksms.book.common.service;

import com.booksms.book.common.data.dto.CategoryDTO;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> findAll();

    CategoryDTO findById(int id);

    CategoryDTO findByName(String name);

    CategoryDTO insert(CategoryDTO request);

    CategoryDTO updateById(int id, CategoryDTO request);

    CategoryDTO deleteById(int id);
}
