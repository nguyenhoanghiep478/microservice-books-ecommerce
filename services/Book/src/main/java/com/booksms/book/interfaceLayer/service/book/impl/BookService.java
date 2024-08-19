package com.booksms.book.interfaceLayer.service.book.impl;

import com.booksms.book.core.domain.entity.Book;
import com.booksms.book.core.domain.exception.BookNotFoundException;
import com.booksms.book.core.domain.exception.InSufficientQuantityException;
import com.booksms.book.infrastructure.JpaRepository.BookJpaRepository;
import com.booksms.book.interfaceLayer.DTO.OrderItemDTO;
import com.booksms.book.interfaceLayer.DTO.OrdersDTO;
import com.booksms.book.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.book.interfaceLayer.DTO.Request.ShortBookDTO;
import com.booksms.book.interfaceLayer.DTO.Request.UpdateQuantityDTO;
import com.booksms.book.interfaceLayer.DTO.Response.BookResponseDTO;
import com.booksms.book.interfaceLayer.DTO.ResponseOrderCreated;
import com.booksms.book.interfaceLayer.service.book.IBookService;
import com.booksms.book.interfaceLayer.service.book.ICreateBookService;
import com.booksms.book.interfaceLayer.service.book.IFindBookService;
import com.booksms.book.interfaceLayer.service.book.IUpdateBookService;
import com.booksms.book.web.mapper.GenericMapper;
import com.sun.jdi.InternalException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.booksms.book.core.domain.constant.STATIC_VAR.IMAGE_STORAGE_PATH;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService{
    private final GenericMapper<Book, BookRequestDTO, BookRequestDTO> bookMapper;
    private final GenericMapper<Book, ShortBookDTO, ShortBookDTO> shortBookMapper;
    @Getter
    private final IFindBookService findBookService;
    private final ICreateBookService createBookService;
    private final IUpdateBookService updateBookService;
    private final BookJpaRepository bookJpaRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, ResponseOrderCreated> kafkaTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookRequestDTO insert(BookRequestDTO request) throws IOException {
        Book book = createBookService.insert(request);
        return bookMapper.toResponse(book, BookRequestDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookRequestDTO updateById(int id, BookRequestDTO request) {
        Book book = updateBookService.updateById(id, request);
        return  bookMapper.toResponse(book, BookRequestDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookRequestDTO deleteById(int id) {
        Optional<Book> book = bookJpaRepository.findById(id);
        if(book.isEmpty()){
            throw new BookNotFoundException(String.format("Book with id %s not found", id));
        }
        bookJpaRepository.deleteById(id);
        BookRequestDTO bookRequestDTO = bookMapper.toResponse(book.get(), BookRequestDTO.class);
        if(bookRequestDTO ==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return bookRequestDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookRequestDTO updateQuantityById(int id, UpdateQuantityDTO request) {
        Book book = updateBookService.updateQuantityById(id, request);
        return bookMapper.toResponse(book, BookRequestDTO.class);
    }

    @KafkaListener(id = "consumer-created-order",topics = "order-created")
    @Transactional(rollbackFor = Exception.class)
    public void updateQuantity(OrdersDTO ordersDTO){
        try{
            for (OrderItemDTO item : ordersDTO.getOrderItems()) {
                updateQuantityById(item.getBookId(), UpdateQuantityDTO.builder()
                        .quantity(item.getTotalQuantity())
                        .type(ordersDTO.getOrderType())
                        .build());
            }

            kafkaTemplate.send("order-created-response", ResponseOrderCreated.builder()
                            .serviceName("BookService")
                            .message("successful")
                    .build());
        }catch (InSufficientQuantityException e){
            kafkaTemplate.send("order-created-response", ResponseOrderCreated.builder()
                    .serviceName("BookService")
                    .message(e.getMessage())
                    .build());
        }


    }

    @Override
    public List<BookRequestDTO> findByCategoryIdAndName(int categoryId, String name) {
        List<Book> books = findBookService.findByCategoryIdAndName(categoryId,name);
        return bookMapper.toListResponse(books, BookRequestDTO.class);
    }

    @Override
    public List<BookRequestDTO> findByCategoryId(int categoryId) {
        List<Book> books = findBookService.findByCategoryId(categoryId);
        return bookMapper.toListResponse(books, BookRequestDTO.class);
    }

    @Override
    public List<BookRequestDTO> findAllLikeNameAndCategoryId(int categoryId, String name) {
        List<Book> books = findBookService.findByCategoryIdAndName(categoryId,name);
        return bookMapper.toListResponse(books, BookRequestDTO.class);
    }

    @Override
    public BookRequestDTO findByName(String name) {
        Book book = findBookService.findByName(name);
        return bookMapper.toResponse(book, BookRequestDTO.class);
    }

    @Override
    public BookRequestDTO findById(int id) {
        Book book = findBookService.findById(id);
        return bookMapper.toResponse(book, BookRequestDTO.class);
    }

    @Override
    public List<ShortBookDTO> findAllInStock() {
        List<Book> books = findBookService.findAllInStock();
        return shortBookMapper.toListResponse(books,ShortBookDTO.class);
    }

    @Override
    public List<BookResponseDTO> findAll() throws IOException {
        List<Book> books = findBookService.findAll();
        Path filePath;
        List<BookResponseDTO> bookRequestDTOList = new ArrayList<>();
        for (Book book : books) {
            filePath = Paths.get(IMAGE_STORAGE_PATH).resolve(book.getImage()).normalize();
            File file = filePath.toFile();
            BookResponseDTO bookRequestDTO = modelMapper.map(book, BookResponseDTO.class);
            if (file.exists()) {
                byte[] fileContent = Files.readAllBytes(filePath);

                String base64Image = Base64.getEncoder().encodeToString(fileContent);
                bookRequestDTO.setImage(base64Image);
            }
            bookRequestDTOList.add(bookRequestDTO);
        }

        return bookRequestDTOList;
    }

    @Override
    public List<BookRequestDTO> findAll(Pageable pageable) {
        return bookMapper.toListResponse(findBookService.findAll(pageable),BookRequestDTO.class);
    }

    @Override
    public List<ShortBookDTO> findAllByIds(Set<Integer> ids) {
        List<ShortBookDTO> result = new ArrayList<>();
        for(Integer id : ids){
            result.add(modelMapper.map(findById(id),ShortBookDTO.class));
        }
        return result;
    }

}
