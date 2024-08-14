package com.booksms.book.web.anonymous;

import com.booksms.book.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.book.interfaceLayer.DTO.Request.ShortBookDTO;
import com.booksms.book.interfaceLayer.DTO.Response.ResponseDTO;
import com.booksms.book.interfaceLayer.service.book.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/book/anonymous")
@RequiredArgsConstructor
public class BookAnonymousController {
    private final IBookService service;
    @GetMapping("/get-all")
    public ResponseEntity<?> GetAllBook() {
        //get listDTO
        List<BookRequestDTO> results = service.findAll();
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("getAllBookSuccessful"))
                .status(201)
                //return list dto
                .result(results)
                .build());
    }
    @GetMapping("/get-all-with-pageable")
    public ResponseEntity<?> GetAllBookWithPageable(Pageable pageable) {
        List<BookRequestDTO> results = service.findAll(pageable);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("getAllBookSuccessful"))
                .status(201)
                //return list dto
                .result(results)
                .build());
    }
    @GetMapping("/get-all-in-stock")
    public ResponseEntity<?> GetAllBookInStock() {
        //get listDTO
        List<ShortBookDTO> results = service.findAllInStock();
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("getAllBookSuccessful"))
                .status(201)
                //return list dto
                .result(results)
                .build());
    }
    @PostMapping("/get-all-by-ids")
    public ResponseEntity<?> GetAllBookByIds(@RequestBody Set<Integer> ids) {
        List<ShortBookDTO> results =  service.findAllByIds(ids);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("getAllBookByIdsSuccessful"))
                .status(201)
                //return list dto
                .result(results)
                .build());
    }
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> GetBookById(@PathVariable int id) {
        //get bookDTO
        BookRequestDTO result = service.findById(id);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("getBookByIdSuccessful"))
                .status(200)
                .result(result)
                .build()
        );
    }
    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<?> GetBookByName(@PathVariable String name) {
        BookRequestDTO result = service.findByName(name);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("getBookByNameSuccessful"))
                .status(200)
                .result(result)
                .build()
        );
    }
    @GetMapping("/get-by-categoryId/{categoryId}")
    public ResponseEntity<?> GetBooksByCategory(@PathVariable int categoryId) {
        List<BookRequestDTO> result = service.findByCategoryId(categoryId);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("getBooksByCategorySuccessful"))
                .status(200)
                .result(result)
                .build()
        );
    }
    @GetMapping("/get-all-by-name/{name}/{categoryId}")
    public ResponseEntity<?> GetBooksByCategoryAndName(@PathVariable String name,@PathVariable int categoryId) {
        List<BookRequestDTO> result = service.findByCategoryIdAndName(categoryId,name);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("getBooksByCategorySuccessful"))
                .status(200)
                .result(result)
                .build()
        );
    }
    @GetMapping("/get-all-like-name-and-categoryId/{name}/{categoryId}")
    public ResponseEntity<?> GetBookLikeNameAndCategoryId(@PathVariable String name,@PathVariable int categoryId) {
        List<BookRequestDTO> result = service.findAllLikeNameAndCategoryId(categoryId, name);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("GetBookLikeNameAndCategoryIdSuccessful"))
                .status(200)
                .result(result)
                .build()
        );
    }
}
