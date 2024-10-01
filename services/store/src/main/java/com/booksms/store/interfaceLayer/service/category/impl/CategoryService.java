package com.booksms.store.interfaceLayer.service.category.impl;

import com.booksms.store.core.domain.entity.Category;
import com.booksms.store.infrastructure.JpaRepository.CategoryJpaRepository;
import com.booksms.store.interfaceLayer.DTO.Request.CategoryDTO;
import com.booksms.store.interfaceLayer.service.category.ICategoryService;
import com.booksms.store.interfaceLayer.service.category.ICreateCategoryService;
import com.booksms.store.interfaceLayer.service.category.IFindCategoryService;
import com.booksms.store.interfaceLayer.service.category.IUpdateCategoryService;
import com.booksms.store.web.mapper.GenericMapper;
import com.sun.jdi.InternalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryJpaRepository repository;
    private final GenericMapper<Category, CategoryDTO, CategoryDTO> categoryMapper;
    private final ICreateCategoryService createCategoryService;
    private final IFindCategoryService findCategoryService;
    private final IUpdateCategoryService updateCategoryService;

    @Transactional(rollbackFor = {Exception.class})
    public CategoryDTO insert(CategoryDTO request) {
        Category category = this.createCategoryService.insertCategory(request);
        return this.categoryMapper.toResponse(category, CategoryDTO.class);
    }

    @Transactional(rollbackFor = {Exception.class})
    public CategoryDTO updateById(int id, CategoryDTO request) {
        Category category = this.updateCategoryService.updateById(id, request);
        return this.categoryMapper.toResponse(category, CategoryDTO.class);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public CategoryDTO deleteById(int id) {
        Category category = this.findCategoryService.findById(id);
        this.repository.deleteById(id);
        if (category == null) {
            throw new InternalException("some thing wrong , please try again");
        } else {
            return this.categoryMapper.toResponse(category, CategoryDTO.class);
        }
    }

    public List<CategoryDTO> findAll() {
        List<Category> categories = this.findCategoryService.findAll();
        return this.categoryMapper.toListResponse(categories, CategoryDTO.class);
    }

    public CategoryDTO findById(int id) {
        Category category = this.findCategoryService.findById(id);
        return this.categoryMapper.toResponse(category, CategoryDTO.class);
    }

    public CategoryDTO findByName(String name) {
        Category category = this.findCategoryService.findByName(name);
        return this.categoryMapper.toResponse(category, CategoryDTO.class);
    }

}