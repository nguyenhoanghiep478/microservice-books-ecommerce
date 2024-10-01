package com.booksms.store.interfaceLayer.service.category;

import com.booksms.store.core.domain.entity.Category;
import com.booksms.store.interfaceLayer.DTO.Request.CategoryDTO;

public interface ICreateCategoryService {
    Category insertCategory(CategoryDTO category);
}