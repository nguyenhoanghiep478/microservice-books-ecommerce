package com.booksms.book.common.service.impl;

import com.booksms.book.common.data.GenericMapper;
import com.booksms.book.common.data.dto.CategoryDTO;
import com.booksms.book.common.data.entity.Category;
import com.booksms.book.common.data.repository.CategoryRepository;
import com.booksms.book.common.service.ICategoryService;
import com.booksms.book.start.Config.exception.BookNotFoundException;
import com.booksms.book.start.Config.exception.CategoryExistException;
import com.booksms.book.start.Config.exception.CategoryNotFoundException;
import com.sun.jdi.InternalException;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository repository;
    private final GenericMapper<Category,CategoryDTO,CategoryDTO> categoryMapper;
    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = repository.findAll();
        if(categories.isEmpty()){
            throw new InternalException("some thing wrong, please try again");
        }
        List<CategoryDTO> categoryDTOS = categoryMapper.toListResponse(categories,CategoryDTO.class);
        if(categoryDTOS.isEmpty()){
            throw new InternalException("some thing wrong, please try again");
        }
        return categoryDTOS;
    }

    @Override
    public CategoryDTO findById(int id) {
        Optional<Category> category = repository.findById(id);
        if(category.isEmpty()){
            throw new BookNotFoundException(String.format("Book with id %s not found", id));
        }
        CategoryDTO categoryDTO = categoryMapper.toResponse(category.get(), CategoryDTO.class);
        if(categoryDTO==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return categoryDTO;
    }

    @Override
    public CategoryDTO findByName(String name) {
        Optional<Category> category = repository.findByName(name);
        if(category.isEmpty()){
            throw new CategoryNotFoundException(String.format("Book with name %s not found", name));
        }
        CategoryDTO categoryDTO = categoryMapper.toResponse(category.get(), CategoryDTO.class);
        if(categoryDTO==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return categoryDTO;
    }
    public boolean isExistedByName(String name) {
        Optional<Category> category = repository.findByName(name);
        return category.isPresent();
    }
    public boolean isExistedById(int id) {
        Optional<Category> category = repository.findById(id);
        return category.isPresent();
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO insert(CategoryDTO request) {
        if (isExistedByName(request.getName())) {
            throw new CategoryExistException(String.format("Category with name %s already exist", request.getName()));
        }
        Category insertCategory = new Category();
        insertCategory= mergeCategory(insertCategory,request);
        Category category = repository.save(insertCategory);
        CategoryDTO categoryDTO = categoryMapper.toResponse(category, CategoryDTO.class);
        if(categoryDTO==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return categoryDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO updateById(int id,CategoryDTO request) {
        Optional<Category> category = repository.findById(id);
        if(category.isEmpty()){
            throw new BookNotFoundException(String.format("Category with id %s, name %s not found", id,request.getName()));
        }
        Category updateCategory = categoryMapper.toEntity(request, Category.class);
        updateCategory = repository.save(updateCategory);
        CategoryDTO categoryDTO = categoryMapper.toResponse(updateCategory, CategoryDTO.class);
        if(categoryDTO==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return categoryDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO deleteById(int id) {
        CategoryDTO categoryDTO = this.findById(id);
        repository.deleteById(id);
        if(categoryDTO==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return categoryDTO;
    }
    private Category mergeCategory(Category category, CategoryDTO categoryDTO) {
        if(categoryDTO==null){
            throw new BadRequestException("missing request body");
        }
        if(categoryDTO.getDescription()!=null){
            category.setDescription(categoryDTO.getDescription());
        }
        if(categoryDTO.getName()!=null){
            category.setName(categoryDTO.getName());
        }
        return category;
    }
}
