package com.booksms.store.interfaceLayer.service.book.impl;

import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.core.domain.exception.BookExpcetion.BookNotFoundException;
import com.booksms.store.core.domain.exception.InSufficientQuantityException;
import com.booksms.store.infrastructure.JpaRepository.BookJpaRepository;
import com.booksms.store.interfaceLayer.DTO.OrderItemDTO;
import com.booksms.store.interfaceLayer.DTO.OrdersDTO;
import com.booksms.store.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.store.interfaceLayer.DTO.Request.ShortBookDTO;
import com.booksms.store.interfaceLayer.DTO.Request.UpdateQuantityDTO;
import com.booksms.store.interfaceLayer.DTO.Response.BookResponseDTO;
import com.booksms.store.interfaceLayer.DTO.ResponseOrderCreated;
import com.booksms.store.interfaceLayer.service.book.IBookService;
import com.booksms.store.interfaceLayer.service.book.ICreateBookService;
import com.booksms.store.interfaceLayer.service.book.IFindBookService;
import com.booksms.store.interfaceLayer.service.book.IUpdateBookService;
import com.booksms.store.web.mapper.GenericMapper;
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

import static com.booksms.store.core.domain.constant.STATIC_VAR.IMAGE_STORAGE_PATH;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {
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
    public BookRequestDTO updateById(int id, BookRequestDTO request) throws IOException {
        Book book = updateBookService.updateById(id, request);
        return bookMapper.toResponse(book, BookRequestDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookRequestDTO deleteById(int id) {
        Optional<Book> book = bookJpaRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException(String.format("Book with id %s not found", id));
        }
        bookJpaRepository.deleteById(id);
        BookRequestDTO bookRequestDTO = bookMapper.toResponse(book.get(), BookRequestDTO.class);
        if (bookRequestDTO == null) {
            throw new InternalException("some thing wrong , please try again");
        }
        return bookRequestDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookRequestDTO updateQuantityById(int id, UpdateQuantityDTO request) throws IOException {
        Book book = updateBookService.updateQuantityById(id, request);
        return bookMapper.toResponse(book, BookRequestDTO.class);
    }

    @KafkaListener(id = "consumer-created-order", topics = "order-created")
    @Transactional(rollbackFor = Exception.class)
    public void updateQuantity(OrdersDTO ordersDTO) {
        try {
            for (OrderItemDTO item : ordersDTO.getOrderItems()) {
                updateQuantityById(item.getBookId(), UpdateQuantityDTO.builder()
                        .id(item.getBookId())
                        .quantity(item.getTotalQuantity())
                        .type(ordersDTO.getOrderType())
                        .build());
            }

            kafkaTemplate.send("order-created-response", ResponseOrderCreated.builder()
                    .serviceName("BookService")
                    .message("successful")
                    .build());
        } catch (InSufficientQuantityException | IOException e) {
            kafkaTemplate.send("order-created-response", ResponseOrderCreated.builder()
                    .serviceName("BookService")
                    .message(e.getMessage())
                    .build());
        }


    }

    @Override
    public List<BookRequestDTO> findByCategoryIdAndName(int categoryId, String name) {
        List<Book> books = findBookService.findByCategoryIdAndName(categoryId, name);
        return bookMapper.toListResponse(books, BookRequestDTO.class);
    }

    @Override
    public List<BookRequestDTO> findByCategoryId(int categoryId) {
        List<Book> books = findBookService.findByCategoryId(categoryId);
        return bookMapper.toListResponse(books, BookRequestDTO.class);
    }

    @Override
    public List<BookRequestDTO> findAllLikeNameAndCategoryId(int categoryId, String name) {
        List<Book> books = findBookService.findByCategoryIdAndName(categoryId, name);
        return bookMapper.toListResponse(books, BookRequestDTO.class);
    }

    @Override
    public BookRequestDTO findByName(String name) {
        Book book = findBookService.findByName(name);
        return bookMapper.toResponse(book, BookRequestDTO.class);
    }

    @Override
    public BookResponseDTO findById(int id) throws IOException {
        Book book = findBookService.findById(id);


        BookResponseDTO bookResponseDTO = modelMapper.map(book, BookResponseDTO.class);
        bookResponseDTO.setImage(getImageBase64(book.getImage()));
        return bookResponseDTO;
    }

    private BookRequestDTO findBookWithoutEncodeImage(int id) {
        Book book = findBookService.findById(id);
        return modelMapper.map(book, BookRequestDTO.class);
    }

    @Override
    public List<ShortBookDTO> findAllInStock() {
        List<Book> books = findBookService.findAllInStock();
        return shortBookMapper.toListResponse(books, ShortBookDTO.class);
    }

    @Override
    public List<BookResponseDTO> findAll() throws IOException {
        List<Book> books = findBookService.findAll();
        return toResponse(books);
    }


    @Override
    public List<BookRequestDTO> findAll(Pageable pageable) {
        return bookMapper.toListResponse(findBookService.findAll(pageable), BookRequestDTO.class);
    }

    @Override
    public List<ShortBookDTO> findAllByIds(Set<Integer> ids) {
        List<ShortBookDTO> result = new ArrayList<>();
        for (Integer id : ids) {
            result.add(modelMapper.map(findBookWithoutEncodeImage(id), ShortBookDTO.class));
        }
        return result;
    }

    @Override
    public List<BookResponseDTO> findTopSales() throws IOException {
        List<Book> books = findBookService.findTopSales();
        return toResponse(books);
    }

    private List<BookResponseDTO> toResponse(List<Book> books) throws IOException {
        List<BookResponseDTO> response = new ArrayList<>();
        for (Book book : books) {
            BookResponseDTO bookRequestDTO = modelMapper.map(book, BookResponseDTO.class);
            bookRequestDTO.setImage(getImageBase64(book.getImage()));

            response.add(bookRequestDTO);
        }
        return response;
    }

    private String getImageBase64(String path) throws IOException {
        Path filePath = Paths.get(IMAGE_STORAGE_PATH).resolve(path).normalize();
        File file = filePath.toFile();
        if (file.exists()) {
            byte[] fileContent = Files.readAllBytes(filePath);
            return Base64.getEncoder().encodeToString(fileContent);
        }
        return null;
    }

}
