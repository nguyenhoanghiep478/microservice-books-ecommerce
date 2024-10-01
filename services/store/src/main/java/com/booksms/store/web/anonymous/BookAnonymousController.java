package com.booksms.store.web.anonymous;

import com.booksms.store.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.store.interfaceLayer.DTO.Request.ShortBookDTO;
import com.booksms.store.interfaceLayer.DTO.Response.BookResponseDTO;
import com.booksms.store.interfaceLayer.DTO.Response.ResponseDTO;
import com.booksms.store.interfaceLayer.service.book.IBookService;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/v1/book/anonymous"})
@RequiredArgsConstructor
public class BookAnonymousController {
    private final IBookService service;

    @GetMapping({"/get-all"})
    public ResponseEntity<?> GetAllBook() throws IOException {
        List<BookResponseDTO> results = this.service.findAll();
        return ResponseEntity.ok(ResponseDTO.builder().message(List.of("getAllBookSuccessful")).status(201).result(results).build());
    }

    @GetMapping({"/get-all-with-pageable"})
    public ResponseEntity<?> GetAllBookWithPageable(Pageable pageable) {
        List<BookRequestDTO> results = this.service.findAll(pageable);
        return ResponseEntity.ok(ResponseDTO.builder().message(List.of("getAllBookSuccessful")).status(201).result(results).build());
    }

    @GetMapping({"/get-all-in-stock"})
    public ResponseEntity<?> GetAllBookInStock() {
        List<ShortBookDTO> results = this.service.findAllInStock();
        return ResponseEntity.ok(ResponseDTO.builder().message(List.of("getAllBookSuccessful")).status(201).result(results).build());
    }

    @PostMapping({"/get-all-by-ids"})
    public ResponseEntity<?> GetAllBookByIds(@RequestBody Set<Integer> ids) {
        List<ShortBookDTO> results = this.service.findAllByIds(ids);
        return ResponseEntity.ok(ResponseDTO.builder().message(List.of("getAllBookByIdsSuccessful")).status(201).result(results).build());
    }

    @GetMapping({"/get-by-id/{id}"})
    public ResponseEntity<?> GetBookById(@PathVariable int id) throws IOException {
        BookResponseDTO result = this.service.findById(id);
        return ResponseEntity.ok(ResponseDTO.builder().message(List.of("getBookByIdSuccessful")).status(200).result(result).build());
    }

    @GetMapping({"/get-by-name/{name}"})
    public ResponseEntity<?> GetBookByName(@PathVariable String name) {
        BookRequestDTO result = this.service.findByName(name);
        return ResponseEntity.ok(ResponseDTO.builder().message(List.of("getBookByNameSuccessful")).status(200).result(result).build());
    }

    @GetMapping({"/get-by-categoryId/{categoryId}"})
    public ResponseEntity<?> GetBooksByCategory(@PathVariable int categoryId) {
        List<BookRequestDTO> result = this.service.findByCategoryId(categoryId);
        return ResponseEntity.ok(ResponseDTO.builder().message(List.of("getBooksByCategorySuccessful")).status(200).result(result).build());
    }

    @GetMapping({"/get-all-by-name/{name}/{categoryId}"})
    public ResponseEntity<?> GetBooksByCategoryAndName(@PathVariable String name, @PathVariable int categoryId) {
        List<BookRequestDTO> result = this.service.findByCategoryIdAndName(categoryId, name);
        return ResponseEntity.ok(ResponseDTO.builder().message(List.of("getBooksByCategorySuccessful")).status(200).result(result).build());
    }

    @GetMapping({"/get-all-like-name-and-categoryId/{name}/{categoryId}"})
    public ResponseEntity<?> GetBookLikeNameAndCategoryId(@PathVariable String name, @PathVariable int categoryId) {
        List<BookRequestDTO> result = this.service.findAllLikeNameAndCategoryId(categoryId, name);
        return ResponseEntity.ok(ResponseDTO.builder().message(List.of("GetBookLikeNameAndCategoryIdSuccessful")).status(200).result(result).build());
    }

    @GetMapping({"/get-top-sales"})
    public ResponseEntity<?> GetTopSales() throws IOException {
        List<BookResponseDTO> result = this.service.findTopSales();
        return ResponseEntity.ok(ResponseDTO.builder().message(List.of("Get top sales successful")).status(200).result(result).build());
    }

}
