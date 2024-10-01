package com.booksms.store.web.controller;

import com.booksms.store.interfaceLayer.DTO.Request.CategoryDTO;
import com.booksms.store.interfaceLayer.DTO.Response.ResponseDTO;
import com.booksms.store.interfaceLayer.service.category.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/v1/category"})
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService service;

    @GetMapping({"/get-all"})
    public ResponseEntity<?> getAll() {
        List<CategoryDTO> results = this.service.findAll();
        return ResponseEntity.ok(ResponseDTO.builder().message(List.of("getAllBookSuccessful")).status(201).result(results).build());
    }

    @GetMapping({"/get-by-id/{id}"})
    public ResponseEntity<?> GetById(@PathVariable int id) {
        CategoryDTO result = this.service.findById(id);
        return ResponseEntity.ok(ResponseDTO.builder().message(List.of("getBookByIdSuccessful")).status(200).result(result).build());
    }

    @GetMapping({"/get-by-name/{name}"})
    public ResponseEntity<?> GetByName(@PathVariable String name) {
        CategoryDTO result = this.service.findByName(name);
        return ResponseEntity.ok(ResponseDTO.builder().message(List.of("getBookByNameSuccessful")).status(200).result(result).build());
    }

    @PostMapping({"/create"})
    public ResponseEntity<?> Create(@RequestBody @Valid CategoryDTO request) {
        CategoryDTO result = this.service.insert(request);
        return ResponseEntity.ok(ResponseDTO.builder().message(List.of("createBookSuccessful")).status(200).result(result).build());
    }

    @PutMapping({"/update-by-id/{id}"})
    public ResponseEntity<?> UpdateById(@PathVariable int id, @RequestBody @Valid CategoryDTO request) {
        CategoryDTO result = this.service.updateById(id, request);
        return ResponseEntity.ok(ResponseDTO.builder().message(List.of("updateBookSuccessful")).status(200).result(result).build());
    }

    @DeleteMapping({"/delete-by-id/{id}"})
    public ResponseEntity<?> DeleteById(@PathVariable int id) {
        CategoryDTO result = this.service.deleteById(id);
        return ResponseEntity.ok(ResponseDTO.builder().message(List.of("deleteBookSuccessful")).status(200).result(result).build());
    }
}
