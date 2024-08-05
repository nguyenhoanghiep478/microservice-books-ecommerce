package com.booksms.book.interfaceLayer.service.book.impl;

import com.booksms.book.infrastructure.JpaRepository.BookJpaRepository;
import com.booksms.book.web.mapper.GenericMapper;
import com.booksms.book.interfaceLayer.DTO.Request.BookRequestDTO;
import com.booksms.book.interfaceLayer.DTO.Request.ShortBookDTO;
import com.booksms.book.interfaceLayer.DTO.Request.UpdateQuantityDTO;
import com.booksms.book.core.domain.entity.Book;
import com.booksms.book.core.domain.exception.BookNotFoundException;
import com.booksms.book.interfaceLayer.service.book.IBookService;
import com.booksms.book.interfaceLayer.service.book.ICreateBookService;
import com.booksms.book.interfaceLayer.service.book.IFindBookService;
import com.booksms.book.interfaceLayer.service.book.IUpdateBookService;
import com.sun.jdi.InternalException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookRequestDTO insert(BookRequestDTO request) {
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
    public List<BookRequestDTO> findAll() {
        List<Book> books = findBookService.findAll();
        return bookMapper.toListResponse(books, BookRequestDTO.class);
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
