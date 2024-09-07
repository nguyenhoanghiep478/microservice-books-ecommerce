package com.booksms.store.web.controller;

import com.booksms.store.core.domain.exception.UpdateFailureException;
import com.booksms.store.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.store.interfaceLayer.DTO.Request.UpdateQuantityDTO;
import com.booksms.store.interfaceLayer.DTO.Response.ResponseDTO;
import com.booksms.store.interfaceLayer.service.book.IBookService;
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
    public ResponseEntity<?> UpdateBookQuantity( @RequestBody @Valid UpdateQuantityDTO request) throws IOException {
        BookRequestDTO bookRequestDTO = service.updateQuantityById(request.getId(), request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(Arrays.asList("updateBookQuantitySuccessful"))
                .status(200)
                .result(bookRequestDTO)
                .build());
    }

    @PutMapping("/update-book-by-id/{id}")
    public ResponseEntity<?> UpdateBookById(@PathVariable int id,
                                            @RequestParam(value = "image",required = false) MultipartFile image,
                                            @RequestParam(value = "title",required = false) String title,
                                            @RequestParam(value = "name",required = false) String name,
                                            @RequestParam(value = "price",required = false) Double price,
                                            @RequestParam(value = "quantity",required = false) Integer quantity,
                                            @RequestParam(value = "isInStock",required = false) Boolean isInStock,
                                            @RequestParam(value = "categoryId",required = false) String categoryId) throws IOException {

        Boolean shouldUpdate = Boolean.FALSE;
        BookRequestDTO request =new BookRequestDTO();
        if(name != null && !name.isEmpty()){
            String[] splitName= name.split(" ");
            Integer chapter = Integer.valueOf(splitName[splitName.length-1]);
            shouldUpdate = Boolean.TRUE;
            request.setName(name);
            request.setChapter(chapter);
        }
        if(title != null && !title.isEmpty()){
            shouldUpdate = Boolean.TRUE;
            request.setTitle(title);
        }
        if(price != null){
            shouldUpdate = Boolean.TRUE;
            request.setPrice(BigDecimal.valueOf(price));
        }
        if(quantity != null){
            shouldUpdate = Boolean.TRUE;
            request.setAvailableQuantity(quantity);
        }
        if(categoryId != null && !categoryId.isEmpty()){
            shouldUpdate = Boolean.TRUE;
            request.setCategoryId(Integer.valueOf(categoryId));
        }
        if(isInStock != null){
            shouldUpdate = Boolean.TRUE;
            request.setIsInStock(isInStock);
        }
        if(image != null){
            shouldUpdate = Boolean.TRUE;
            request.setImage(image);
        }
        if(!shouldUpdate){
            throw new UpdateFailureException("nothing to update");
        }
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
