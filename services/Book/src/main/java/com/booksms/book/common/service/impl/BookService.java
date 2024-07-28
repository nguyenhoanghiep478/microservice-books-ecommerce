package com.booksms.book.common.service.impl;

import com.booksms.book.common.data.GenericMapper;
import com.booksms.book.common.data.dto.BookDTO;
import com.booksms.book.common.data.dto.OrderType;
import com.booksms.book.common.data.dto.UpdateQuantityDTO;
import com.booksms.book.common.data.entity.Book;
import com.booksms.book.common.data.entity.Category;
import com.booksms.book.common.data.repository.BookRepository;
import com.booksms.book.common.data.repository.CategoryRepository;
import com.booksms.book.common.service.IBookService;
import com.booksms.book.start.Config.exception.BookNotFoundException;
import com.booksms.book.start.Config.exception.CategoryNotFoundException;
import com.booksms.book.start.Config.exception.InSufficientQuantityException;
import com.sun.jdi.InternalException;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService{
    private final BookRepository repository;
    private final CategoryRepository categoryRepository;
    private final GenericMapper<Book,BookDTO,BookDTO> bookMapper;
    @Override
    public List<BookDTO> findAll() {
        List<Book> books = repository.findAll();
        if(books.isEmpty()){
            throw new InternalException("some thing wrong, please try again");
        }
        List<BookDTO> bookDTOS = bookMapper.toListResponse(books,BookDTO.class);
        if(bookDTOS.isEmpty()){
            throw new InternalException("some thing wrong, please try again");
        }
        return bookDTOS;
    }

    @Override
    public BookDTO findById(int id) {
        Optional<Book> book = repository.findById(id);
        if(book.isEmpty()){
            throw new BookNotFoundException(String.format("Book with id %s not found", id));
        }
        BookDTO bookDTO = bookMapper.toResponse(book.get(), BookDTO.class);
        if(bookDTO==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return bookDTO;
    }

    @Override
    public BookDTO findByName(String name) {
        Optional<Book> book = repository.findByName(name);
        if(book.isEmpty()){
            throw new BookNotFoundException(String.format("Book with name %s not found", name));
        }
        BookDTO bookDTO = bookMapper.toResponse(book.get(), BookDTO.class);
        if(bookDTO==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return bookDTO;
    }

    @Override
    public List<BookDTO> findByCategoryId(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () ->  new CategoryNotFoundException(String.format("Category with id %s not found", categoryId))
        );
        List<Book> results = repository.findAllByCategoryId(categoryId);
        if(results.isEmpty()){
            throw new BookNotFoundException(String.format("Book with category %s not found", category.getName()));
        }
        List<BookDTO> bookDTOS = bookMapper.toListResponse(results,BookDTO.class);
        if(bookDTOS.isEmpty()){
            throw new InternalException("some thing wrong , please try again");
        }
        return bookDTOS;
    }

    @Override
    public List<BookDTO> findByCategoryIdAndName(int categoryId, String name) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CategoryNotFoundException(String.format("Category with id %s not found", categoryId))
        );
        List<Book> booksWithCategoryId = repository.findAllByCategoryId(categoryId);
        if(booksWithCategoryId.isEmpty()){
            throw new BookNotFoundException(String.format("Book with category %s not found", category.getName()));
        }
        List<Book> booksWithCategoryIdAndName = booksWithCategoryId.stream().filter(book -> book.getName().equals(name)).toList();
        if(booksWithCategoryIdAndName.isEmpty()){
            throw new BookNotFoundException(String.format("Book with category: %s and book name: %s not found", category.getName(),name));
        }
        List<BookDTO> bookDTOS = bookMapper.toListResponse(booksWithCategoryIdAndName,BookDTO.class);
        if(bookDTOS.isEmpty()){
            throw new InternalException("some thing wrong , please try again");
        }
        return bookDTOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookDTO insert(BookDTO request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                () -> new CategoryNotFoundException(String.format("Category with id %s not found", request.getCategoryId()))
        );
        Optional<Book> bookInDb = repository.findByName(request.getName());
        if(bookInDb.isPresent()){
            BookDTO updateRequest = BookDTO.builder()
                    .availableQuantity(bookInDb.get().getAvailableQuantity()+1)
                    .build();
            return this.updateById(bookInDb.get().getId(), updateRequest);
        }
        Book book = bookMapper.toEntity(request, Book.class);
        book.setCategory(category);
        book = repository.save(book);
        BookDTO bookDTO = bookMapper.toResponse(book, BookDTO.class);
        if(bookDTO==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return bookDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookDTO updateById(int id, BookDTO request) {
        Optional<Book> book = repository.findById(id);
        if(book.isEmpty()){
            throw new BookNotFoundException(String.format("Book with id %s not found", id));
        }
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                () -> new CategoryNotFoundException(String.format("Category with id %s not found", request.getCategoryId()))
        );
        Book mergeBook = bookMapper.toEntity(request, Book.class);
        mergeBook.setCategory(category);
        mergeBook = repository.save(mergeBook);
        BookDTO bookDTO = bookMapper.toResponse(mergeBook, BookDTO.class);
        if(bookDTO==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return bookDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookDTO deleteById(int id) {
        Optional<Book> book = repository.findById(id);
        if(book.isEmpty()){
            throw new BookNotFoundException(String.format("Book with id %s not found", id));
        }
        repository.deleteById(id);
        BookDTO bookDTO = bookMapper.toResponse(book.get(), BookDTO.class);
        if(bookDTO==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return bookDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookDTO updateQuantityById(int id, UpdateQuantityDTO request) {

        Book book = repository.findById(id).orElseThrow(
                () -> new BookNotFoundException(String.format("Book with id %s not found", id))
        );
        if(request.getType() == OrderType.BUY){
            book.setAvailableQuantity(request.getQuantity()+book.getAvailableQuantity());
            book = repository.save(book);
            BookDTO bookDTO = bookMapper.toResponse(book, BookDTO.class);
            if(bookDTO==null){
                throw new InternalException("some thing wrong , please try again");
            }
            return bookDTO;
        }
        if(book.getAvailableQuantity()< request.getQuantity()){
            throw new InSufficientQuantityException("over sufficient quantity,please try again");
        }
        book.setAvailableQuantity(book.getAvailableQuantity()-request.getQuantity());
        book = repository.save(book);
        BookDTO bookDTO = bookMapper.toResponse(book, BookDTO.class);
        if(bookDTO==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return bookDTO;
    }
}
