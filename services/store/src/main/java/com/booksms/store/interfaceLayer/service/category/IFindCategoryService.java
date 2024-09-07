package com.booksms.store.interfaceLayer.service.category;

import com.booksms.store.core.domain.entity.Category;

import java.util.List;

public interface IFindCategoryService {
    List<Category> findAll();

    Category findById(int id);

    Category findByName(String name);
    boolean isExistedByName(String name);
    boolean isExistedById(int id);
}
