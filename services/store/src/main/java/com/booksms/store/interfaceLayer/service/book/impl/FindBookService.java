package com.booksms.store.interfaceLayer.service.book.impl;

import com.booksms.store.application.model.BooksSearchCriteria;
import com.booksms.store.application.usecase.Book.FindUseCase.FindBooksUseCase;
import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.interfaceLayer.DTO.Response.TopSalesDTO;
import com.booksms.store.interfaceLayer.service.book.IFindBookService;
import com.booksms.store.interfaceLayer.servicegateway.OrderServiceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindBookService implements IFindBookService {
    private final FindBooksUseCase findBooksUseCase;
    private final OrderServiceGateway orderServiceGateway;


    @Override
    public List<Book> findAll() {
       return findBooksUseCase.execute(null);
    }

    @Override
    public Book findById(int id) {
        BooksSearchCriteria findById = BooksSearchCriteria.builder()
                .key("id")
                .operation(":")
                .value(id)
                .build();
        return findBooksUseCase.execute(List.of(findById)).get(0);
    }

    @Override
    public Book findByName(String name) {
        BooksSearchCriteria findByName = BooksSearchCriteria.builder()
                .key("name")
                .operation(":")
                .value(name)
                .build();
        BooksSearchCriteria withIsInStock = BooksSearchCriteria.builder()
                .key("isInStock")
                .operation(":")
                .value(true)
                .build();
        return findBooksUseCase.execute(List.of(findByName,withIsInStock)).get(0);
    }

    @Override
    public List<Book> findByCategoryId(int categoryId) {
        BooksSearchCriteria findByCategoryId = BooksSearchCriteria.builder()
                .key("categoryId")
                .operation(":")
                .value(categoryId)
                .build();
        BooksSearchCriteria withIsInStock = BooksSearchCriteria.builder()
                .key("isInStock")
                .operation(":")
                .value(true)
                .build();
        return findBooksUseCase.execute(List.of(findByCategoryId,withIsInStock));
    }

    @Override
    public List<Book> findByCategoryIdAndName(int categoryId, String name) {
        BooksSearchCriteria findByName = BooksSearchCriteria.builder()
                .key("name")
                .operation(":")
                .value(name)
                .build();
        BooksSearchCriteria findByCategoryId = BooksSearchCriteria.builder()
                .key("categoryId")
                .operation(":")
                .value(categoryId)
                .build();
        BooksSearchCriteria withIsInStock = BooksSearchCriteria.builder()
                .key("isInStock")
                .operation(":")
                .value(true)
                .build();
        return findBooksUseCase.execute(List.of(findByName,findByCategoryId,withIsInStock));
    }

    @Override
    public List<Book> findAllInStock() {
        BooksSearchCriteria withIsInStock = BooksSearchCriteria.builder()
                .key("isInStock")
                .operation(":")
                .value(true)
                .build();
        return findBooksUseCase.execute(List.of(withIsInStock));
    }

    @Override
    public List<Book> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public boolean existById(int id) {
        return this.findById(id) != null;
    }

    @Override
    public boolean existByName(String name) {
        return this.findByName(name)!= null;
    }

    @Override
    public List<Book> findOneLikeNameAndCategoryId(String name, int categoryId) {
        BooksSearchCriteria findByName = BooksSearchCriteria.builder()
                .key("name")
                .operation("LIKE")
                .value(String.valueOf(name))
                .build();
        BooksSearchCriteria findByCategoryId = BooksSearchCriteria.builder()
                .key("categoryId")
                .operation(":")
                .value(String.valueOf(categoryId))
                .build();
        return findBooksUseCase.execute(List.of(findByName,findByCategoryId));
    }

    @Override
    public List<Book> findTopSales() {
        List<Integer> ids = orderServiceGateway.getTopSales().stream().map(TopSalesDTO::getBookId).toList();


        BooksSearchCriteria findInIds = BooksSearchCriteria.builder()
                .key("id")
                .operation("IN")
                .value(ids)
                .build();

        return findBooksUseCase.execute(List.of(findInIds));
    }
}
