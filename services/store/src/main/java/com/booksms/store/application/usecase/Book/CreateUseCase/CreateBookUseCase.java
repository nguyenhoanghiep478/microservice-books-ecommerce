package com.booksms.store.application.usecase.Book.CreateUseCase;

import com.booksms.store.application.model.BookModel;
import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.application.usecase.BaseUseCase;
import com.booksms.store.application.usecase.category.FindCategoryUseCase;
import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.core.domain.entity.Category;
import com.booksms.store.core.domain.exception.CreateFailureException;
import com.booksms.store.core.domain.repository.IBookRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateBookUseCase implements BaseUseCase<Book,BookModel> {
    private final IBookRepository bookRepository;
    private final FindCategoryUseCase findCategoryUseCase;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Book execute(@NotNull BookModel bookModel) {

        Book newBook = map(bookModel);

        return execute(newBook,bookModel.getCategoryId());

    }

    @Transactional(rollbackFor = Exception.class)
    public Book execute(Book book,Integer categoryId){
        Category category = findCategoryUseCase.execute(List.of(SearchCriteria.builder()
                .key("id")
                .operation(":")
                .value(categoryId)
                .build())).get(0);
        book.setCategory(category);

        Book createdBook = bookRepository.save(book).orElseThrow(
                () -> new CreateFailureException(String.format("Book could not be created: %s", book.getName()))
        );

        return bookRepository.save(createdBook).orElseThrow(
                () -> new CreateFailureException(String.format("Book could not be created: %s", createdBook.getName()))
        );
    }

    private Book map(BookModel bookModel) {
        Book book = new Book();
        book.setName(bookModel.getName());
        book.setImage(bookModel.getPathImage());
        if(bookModel.getPrice() == null){
            book.setPrice(BigDecimal.ZERO);
        }else{
            book.setPrice(bookModel.getPrice());
        }
        book.setDistributorId(bookModel.getDistributorId());
        book.setChapter(bookModel.getChapter());
        book.setTitle(bookModel.getTitle());
        book.setIsInStock(bookModel.getIsInStock());
        return book;
    }


}
