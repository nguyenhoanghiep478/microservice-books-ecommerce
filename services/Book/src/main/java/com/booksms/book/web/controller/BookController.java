package com.booksms.book.web.controller;

import com.booksms.book.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.book.interfaceLayer.DTO.Response.ResponseDTO;
import com.booksms.book.interfaceLayer.DTO.Request.ShortBookDTO;
import com.booksms.book.interfaceLayer.DTO.Request.UpdateQuantityDTO;
import com.booksms.book.interfaceLayer.service.book.IBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {
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
    @GetMapping("/get-books-and-distributor-by-bookId/{id}")
    public ResponseEntity<?> GetBooksAndDistributors(@PathVariable int id) {
        //get bookDTO


        //get distributorDTo


        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("getBookByIdSuccessful"))
                .status(200)
                .result(null)
                .build()
        );
    }
    @GetMapping("/get-book-and-author-by-bookId/{id}")
    public ResponseEntity<?> GetBookAndAuthor(@PathVariable int id) {
        //get bookDTO


        //get AuthorDTO


        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("getBookByIdSuccessful"))
                .status(200)
                .result(null)
                .build()
        );
    }
    @GetMapping("/get-book-and-author-and-contributor-by-bookId/{id}")
    public ResponseEntity<?> GetBookAndAuthorAndContributor(@PathVariable int id) {
        //get bookDTO


        //get AuthorDTO


        //get ContributorDTO

        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("getBookByIdSuccessful"))
                .status(200)
                .result(null)
                .build()
        );
    }
    @PostMapping("/create")
    public ResponseEntity<?> CreateBook(@RequestBody @Valid BookRequestDTO request){
        //create new book
        BookRequestDTO result = service.insert(request);
        System.out.println(result.getIsInStock());
        return ResponseEntity.ok(ResponseDTO.builder()
                        .message(Arrays.asList("createBookSuccessful"))
                        .status(200)
                        .result(result)
                        .build());
    }
    @PostMapping("/update-quantity")
    public ResponseEntity<?> UpdateBookQuantity( @RequestBody @Valid UpdateQuantityDTO request){
        BookRequestDTO bookRequestDTO = service.updateQuantityById(request.getId(), request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("updateBookQuantitySuccessful"))
                .status(200)
                .result(bookRequestDTO)
                .build());
    }

    @PutMapping("/update-book-by-id/{id}")
    public ResponseEntity<?> UpdateBookById(@PathVariable int id,@RequestBody @Valid BookRequestDTO request){
        //update new book
        BookRequestDTO result = service.updateById(id,request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("updateBookSuccessful"))
                .status(200)
                .result(result)
                .build());
    }
    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity<?> DeleteBookById(@PathVariable int id){
        //delete new book
        BookRequestDTO result = service.deleteById(id);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("deleteBookSuccessful"))
                .status(200)
                .result(result)
                .build());
    }
}
