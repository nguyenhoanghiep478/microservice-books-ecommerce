package com.booksms.book.interfaceLayer.service.category;

import com.booksms.book.core.domain.entity.Category;
import com.booksms.book.interfaceLayer.DTO.Request.CategoryDTO;

public interface IUpdateCategoryService {
    Category updateById(int id, CategoryDTO request);
}
