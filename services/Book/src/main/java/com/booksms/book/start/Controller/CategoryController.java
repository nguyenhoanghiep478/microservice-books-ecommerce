package com.booksms.book.start.Controller;

import com.booksms.book.common.data.dto.BookDTO;
import com.booksms.book.common.data.dto.CategoryDTO;
import com.booksms.book.common.data.dto.ResponseDTO;
import com.booksms.book.common.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.ErrorPageSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService service;
    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        List<CategoryDTO> results = service.findAll();
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("getAllBookSuccessful"))
                .status(201)
                //return list dto
                .result(results)
                .build());
    }
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> GetById(@PathVariable int id) {
        //get bookDTO
        CategoryDTO result = service.findById(id);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("getBookByIdSuccessful"))
                .status(200)
                .result(result)
                .build()
        );
    }
    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<?> GetByName(@PathVariable String name) {
        CategoryDTO result = service.findByName(name);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("getBookByNameSuccessful"))
                .status(200)
                .result(result)
                .build()
        );
    }
    @PostMapping("/create")
    public ResponseEntity<?> Create(@RequestBody @Valid CategoryDTO request){
        //create new book
        CategoryDTO result = service.insert(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("createBookSuccessful"))
                .status(200)
                .result(result)
                .build());
    }
    @PutMapping("/update-by-id/{id}")
    public ResponseEntity<?> UpdateById(@PathVariable int id,@RequestBody @Valid CategoryDTO request){
        //update new book
        CategoryDTO result = service.updateById(id,request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("updateBookSuccessful"))
                .status(200)
                .result(result)
                .build());
    }
    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<?> DeleteById(@PathVariable int id){
        //delete new book
        CategoryDTO result = service.deleteById(id);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("deleteBookSuccessful"))
                .status(200)
                .result(result)
                .build());
    }
}
