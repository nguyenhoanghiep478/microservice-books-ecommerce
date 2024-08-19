package com.booksms.book.web.controller;

import com.booksms.book.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.book.interfaceLayer.DTO.Request.UpdateQuantityDTO;
import com.booksms.book.interfaceLayer.DTO.Response.ResponseDTO;
import com.booksms.book.interfaceLayer.service.book.IBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {
    private final IBookService service;

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
    public ResponseEntity<?> CreateBook(
            @RequestParam("image") MultipartFile image,
            @RequestParam("title") String title,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("selectedCategory") String selectedCategory) throws IOException {
        //create new book
        String[] splitName= name.split(" ");
        Integer chapter = Integer.valueOf(splitName[splitName.length-1]);

        BookRequestDTO request = BookRequestDTO.builder()
                .image(image)
                .title(title)
                .name(name)
                .price(BigDecimal.valueOf(price))
                .availableQuantity(quantity)
                .categoryId(Integer.valueOf(selectedCategory))
                .chapter(chapter)
                .distributorId(1)
                .isInStock(true)
                .build();

        BookRequestDTO result = service.insert(request);
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
