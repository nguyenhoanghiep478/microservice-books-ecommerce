package com.booksms.book.common.service.impl;

import com.booksms.book.common.data.GenericMapper;
import com.booksms.book.common.data.dto.OrderType;
import com.booksms.book.common.data.dto.Request.BookRequestDTO;
import com.booksms.book.common.data.dto.ShortBookDTO;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService{
    private final BookRepository repository;
    private final CategoryRepository categoryRepository;
    private final GenericMapper<Book, BookRequestDTO, BookRequestDTO> bookMapper;
    private final GenericMapper<Book, ShortBookDTO, ShortBookDTO> shortBookMapper;

    @Override
    public List<BookRequestDTO> findAll() {
        List<Book> books = repository.findAll();
        if(books.isEmpty()){
            throw new InternalException("some thing wrong, please try again");
        }
        List<BookRequestDTO> bookRequestDTOS = bookMapper.toListResponse(books, BookRequestDTO.class);
        if(bookRequestDTOS.isEmpty()){
            throw new InternalException("some thing wrong, please try again");
        }
        return bookRequestDTOS;
    }

    @Override
    public BookRequestDTO findById(int id) {
        Optional<Book> book = repository.findById(id);
        if(book.isEmpty()){
            throw new BookNotFoundException(String.format("Book with id %s not found", id));
        }
        BookRequestDTO bookRequestDTO = bookMapper.toResponse(book.get(), BookRequestDTO.class);
        if(bookRequestDTO ==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return bookRequestDTO;
    }

    @Override
    public BookRequestDTO findByName(String name) {
        Optional<Book> book = repository.findByName(name);
        if(book.isEmpty()){
            throw new BookNotFoundException(String.format("Book with name %s not found", name));
        }
        BookRequestDTO bookRequestDTO = bookMapper.toResponse(book.get(), BookRequestDTO.class);
        if(bookRequestDTO ==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return bookRequestDTO;
    }

    @Override
    public List<BookRequestDTO> findByCategoryId(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () ->  new CategoryNotFoundException(String.format("Category with id %s not found", categoryId))
        );
        List<Book> results = repository.findAllByCategoryId(categoryId);
        if(results.isEmpty()){
            throw new BookNotFoundException(String.format("Book with category %s not found", category.getName()));
        }
        List<BookRequestDTO> bookRequestDTOS = bookMapper.toListResponse(results, BookRequestDTO.class);
        if(bookRequestDTOS.isEmpty()){
            throw new InternalException("some thing wrong , please try again");
        }
        return bookRequestDTOS;
    }

    @Override
    public List<BookRequestDTO> findByCategoryIdAndName(int categoryId, String name) {
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
        List<BookRequestDTO> bookRequestDTOS = bookMapper.toListResponse(booksWithCategoryIdAndName, BookRequestDTO.class);
        if(bookRequestDTOS.isEmpty()){
            throw new InternalException("some thing wrong , please try again");
        }
        return bookRequestDTOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookRequestDTO insert(BookRequestDTO request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                () -> new CategoryNotFoundException(String.format("Category with id %s not found", request.getCategoryId()))
        );
        Optional<Book> bookInDb = repository.findByName(request.getName());
        if(bookInDb.isPresent()){
            int addAmount = 1;
            if(request.getAvailableQuantity() != null){
                addAmount = request.getAvailableQuantity();
            }
            BookRequestDTO updateRequest = BookRequestDTO.builder()
                    .availableQuantity(bookInDb.get().getAvailableQuantity()+addAmount)
                    .categoryId(request.getCategoryId())
                    .build();
            return this.updateById(bookInDb.get().getId(), updateRequest);
        }
        Book book = bookMapper.toEntity(request, Book.class);
        book.setCategory(category);
        book = repository.save(book);
        BookRequestDTO bookRequestDTO = bookMapper.toResponse(book, BookRequestDTO.class);
        if(bookRequestDTO ==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return bookRequestDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookRequestDTO updateById(int id, BookRequestDTO request) {
        Optional<Book> book = repository.findById(id);
        if(book.isEmpty()){
            throw new BookNotFoundException(String.format("Book with id %s not found", id));
        }
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                () -> new CategoryNotFoundException(String.format("Category with id %s not found", request.getCategoryId()))
        );
        Book mergeBook = mergeBook(book.get(),request);
        repository.save(mergeBook);
        BookRequestDTO bookRequestDTO = bookMapper.toResponse(mergeBook, BookRequestDTO.class);
        if(bookRequestDTO ==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return bookRequestDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookRequestDTO deleteById(int id) {
        Optional<Book> book = repository.findById(id);
        if(book.isEmpty()){
            throw new BookNotFoundException(String.format("Book with id %s not found", id));
        }
        repository.deleteById(id);
        BookRequestDTO bookRequestDTO = bookMapper.toResponse(book.get(), BookRequestDTO.class);
        if(bookRequestDTO ==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return bookRequestDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookRequestDTO updateQuantityById(int id, UpdateQuantityDTO request) {

        Book book = repository.findById(id).orElseThrow(
                () -> new BookNotFoundException(String.format("Book with id %s not found", id))
        );
        if(request.getType() == OrderType.BUY){
            book.setAvailableQuantity(request.getQuantity()+book.getAvailableQuantity());
            book = repository.save(book);
            BookRequestDTO bookRequestDTO = bookMapper.toResponse(book, BookRequestDTO.class);
            if(bookRequestDTO ==null){
                throw new InternalException("some thing wrong , please try again");
            }
            return bookRequestDTO;
        }
        if(book.getAvailableQuantity()< request.getQuantity()){
            throw new InSufficientQuantityException("over sufficient quantity,please try again");
        }
        book.setAvailableQuantity(book.getAvailableQuantity()-request.getQuantity());
        book = repository.save(book);
        BookRequestDTO bookRequestDTO = bookMapper.toResponse(book, BookRequestDTO.class);
        if(bookRequestDTO ==null){
            throw new InternalException("some thing wrong , please try again");
        }
        return bookRequestDTO;
    }

    @Override
    public List<ShortBookDTO> findAllInStock() {
        List<Book> books = repository.findByIsInStockTrue();
        if(books.isEmpty()){
            return null;
        }
        List<ShortBookDTO> shortBookDTOS = shortBookMapper.toListResponse(books, ShortBookDTO.class);
        if(shortBookDTOS == null || shortBookDTOS.isEmpty()){
            throw new InternalException("some thing wrong , please try again");
        }
        return shortBookDTOS;
    }

    private Book mergeBook(Book book, BookRequestDTO bookRequestDTO) {
       if(bookRequestDTO.getName()!= null && !Objects.equals(book.getName(), bookRequestDTO.getName())){
           book.setName(bookRequestDTO.getName());
       }
       if( bookRequestDTO.getAvailableQuantity() != null && book.getAvailableQuantity()!= bookRequestDTO.getAvailableQuantity()){
           book.setAvailableQuantity(bookRequestDTO.getAvailableQuantity());
       }
       if( bookRequestDTO.getCategoryId() != null && book.getCategory().getId() != bookRequestDTO.getCategoryId()){
           Category category = categoryRepository.findById(bookRequestDTO.getCategoryId()).orElseThrow(
                   ()-> new CategoryNotFoundException(String.format("Category with id %s not found", bookRequestDTO.getCategoryId()))
           );
           book.setCategory(category);
       }
       if(bookRequestDTO.getPrice()!= null && !Objects.equals(book.getPrice(), bookRequestDTO.getPrice())){
           book.setPrice(bookRequestDTO.getPrice());
       }
       if(bookRequestDTO.getChapter() != null && !Objects.equals(book.getChapter(), bookRequestDTO.getChapter())){
           book.setChapter(bookRequestDTO.getChapter());
       }
       if(bookRequestDTO.getTitle() != null && !Objects.equals(book.getTitle(), bookRequestDTO.getTitle())){
           book.setTitle(bookRequestDTO.getTitle());
       }
       book.setInStock(bookRequestDTO.isInStock());
       return book;
    }
}
