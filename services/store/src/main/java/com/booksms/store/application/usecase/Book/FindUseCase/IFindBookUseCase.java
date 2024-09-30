package com.booksms.store.application.usecase.Book.FindUseCase;

import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.core.domain.entity.Book;

import java.util.List;

public interface IFindBookUseCase {
    List<Book> execute(List<SearchCriteria> SearchCriteria);
}
