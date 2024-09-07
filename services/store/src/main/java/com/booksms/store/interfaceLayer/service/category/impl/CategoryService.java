package com.booksms.store.interfaceLayer.service.category.impl;

import com.booksms.store.core.domain.entity.Category;
import com.booksms.store.infrastructure.JpaRepository.CategoryJpaRepository;
import com.booksms.store.interfaceLayer.DTO.Request.CategoryDTO;
import com.booksms.store.interfaceLayer.service.category.ICategoryService;
import com.booksms.store.interfaceLayer.service.category.ICreateCategoryService;
import com.booksms.store.interfaceLayer.service.category.IFindCategoryService;
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
    private final GenericMapper<Category,CategoryDTO,CategoryDTO> categoryMapper;
    private final ICreateCategoryService createCategoryService;
    private final IFindCategoryService findCategoryService;
    private final UpdateCategoryService updateCategoryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO insert(CategoryDTO request) {
        Category category = createCategoryService.insertCategory(request);
        return categoryMapper.toResponse(category,CategoryDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO updateById(int id,CategoryDTO request) {
       Category category = updateCategoryService.updateById(id,request);
       return categoryMapper.toResponse(category,CategoryDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO deleteById(int id) {
        Category category = findCategoryService.findById(id);
        repository.deleteById(id);
        if(category==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return categoryMapper.toResponse(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = findCategoryService.findAll();
        return categoryMapper.toListResponse(categories, CategoryDTO.class);
    }

    @Override
    public CategoryDTO findById(int id) {
        Category category = findCategoryService.findById(id);
        return categoryMapper.toResponse(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO findByName(String name) {
        Category category = findCategoryService.findByName(name);
        return categoryMapper.toResponse(category, CategoryDTO.class);
    }

}
