package com.bookms.order.application.servicegateway;

import com.bookms.order.application.model.BookModel;
import com.bookms.order.application.model.UpdateQuantityModel;

import java.util.List;
import java.util.Set;

public interface IBookServiceGateway {
    BookModel updateQuantity(UpdateQuantityModel updateQuantityModel);

    List<BookModel> findAllBookWithListId(Set<Integer> booksId);

}
