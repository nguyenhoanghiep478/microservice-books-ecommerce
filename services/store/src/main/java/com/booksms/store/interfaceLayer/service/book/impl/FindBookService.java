package com.booksms.store.interfaceLayer.service.book.impl;

import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.application.usecase.Book.FindUseCase.impl.FindBooksUseCase;
import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.interfaceLayer.DTO.Response.TopSalesDTO;
import com.booksms.store.interfaceLayer.service.book.IFindBookService;
import com.booksms.store.interfaceLayer.servicegateway.OrderServiceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
        SearchCriteria findById = SearchCriteria.builder()
                .key("id")
                .operation(":")
                .value(id)
                .build();
        return findBooksUseCase.execute(List.of(findById)).get(0);
    }

    @Override
    public Book findByName(String name) {
        SearchCriteria findByName = SearchCriteria.builder()
                .key("name")
                .operation(":")
                .value(name)
                .build();
        SearchCriteria withIsInStock = SearchCriteria.builder()
                .key("isInStock")
                .operation(":")
                .value(true)
                .build();
        return findBooksUseCase.execute(List.of(findByName,withIsInStock)).get(0);
    }

    @Override
    public List<Book> findByCategoryId(int categoryId) {
        SearchCriteria findByCategoryId = SearchCriteria.builder()
                .key("categoryId")
                .operation(":")
                .value(categoryId)
                .build();
        SearchCriteria withIsInStock = SearchCriteria.builder()
                .key("isInStock")
                .operation(":")
                .value(true)
                .build();
        return findBooksUseCase.execute(List.of(findByCategoryId,withIsInStock));
    }

    @Override
    public List<Book> findByCategoryIdAndName(int categoryId, String name) {
        SearchCriteria findByName = SearchCriteria.builder()
                .key("name")
                .operation(":")
                .value(name)
                .build();
        SearchCriteria findByCategoryId = SearchCriteria.builder()
                .key("categoryId")
                .operation(":")
                .value(categoryId)
                .build();
        SearchCriteria withIsInStock = SearchCriteria.builder()
                .key("isInStock")
                .operation(":")
                .value(true)
                .build();
        return findBooksUseCase.execute(List.of(findByName,findByCategoryId,withIsInStock));
    }

    @Override
    public List<Book> findAllInStock() {
        SearchCriteria withIsInStock = SearchCriteria.builder()
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
        SearchCriteria findByName = SearchCriteria.builder()
                .key("name")
                .operation("LIKE")
                .value(String.valueOf(name))
                .build();
        SearchCriteria findByCategoryId = SearchCriteria.builder()
                .key("categoryId")
                .operation(":")
                .value(String.valueOf(categoryId))
                .build();
        return findBooksUseCase.execute(List.of(findByName,findByCategoryId));
    }

    @Override
    public List<Book> findTopSales() {
        List<Integer> ids = orderServiceGateway.getTopSales().stream().map(TopSalesDTO::getBookId).toList();
       return findBookInIds(ids);
    }

    @Override
    public List<Book> findBookInIds(List<Integer> ids) {
        SearchCriteria findInIds = SearchCriteria.builder()
                .key("id")
                .operation("IN")
                .value(ids)
                .build();

        return findBooksUseCase.execute(List.of(findInIds));
    }
}
