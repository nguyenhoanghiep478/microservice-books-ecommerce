package com.booksms.store.application.usecase.inventory.CreateUseCase;

import com.booksms.store.application.model.BookModel;
import com.booksms.store.application.model.CreateQuantityModel;
import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.application.usecase.Book.CreateUseCase.CreateBookUseCase;
import com.booksms.store.application.usecase.Book.CreateUseCase.CreateInventoryBookUseCase;
import com.booksms.store.application.usecase.Book.FindUseCase.IFindBookUseCase;
import com.booksms.store.application.usecase.Book.UpdateUseCase.BookStartSellUseCase;
import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.core.domain.entity.Inventory;
import com.booksms.store.core.domain.entity.InventoryBook;
import com.booksms.store.core.domain.exception.BookExpcetion.BookNotFoundException;
import com.booksms.store.core.domain.exception.CreateFailureException;
import com.booksms.store.core.domain.exception.InventoryNotExistedException;
import com.booksms.store.core.domain.repository.IBookRepository;
import com.booksms.store.core.domain.repository.IInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateQuantityUseCase {
    private final IInventoryRepository inventoryRepository;
    private final IFindBookUseCase findBooksUseCase;
    private final IBookRepository bookRepository;
    private final CreateInventoryBookUseCase createInventoryBookUseCase;
    private final BookStartSellUseCase bookStartSellUseCase;

    public Book execute(CreateQuantityModel createQuantityModel) {
        Book book = findBooksUseCase.execute(List.of(
                SearchCriteria.builder()
                        .key("id")
                        .operation(":")
                        .value(createQuantityModel.getBookId())
                        .build()
        )).get(0);

        if(book == null) {
            throw new BookNotFoundException(String.format("Book with id '%s' not found", createQuantityModel.getBookId()));
        }

        Inventory inventory = inventoryRepository.findById(createQuantityModel.getInventoryId()).orElseThrow(
                () -> new InventoryNotExistedException("Inventory not found")
        );

        InventoryBook inventoryBook = create(inventory,book,createQuantityModel);

        book.addInventoryBook(inventoryBook);
        book.setPrice(createQuantityModel.getPurchasePrice());
        bookStartSellUseCase.execute(book);

        inventory.addInventoryBook(inventoryBook);

        if(inventoryRepository.save(inventory) != null){
            return book;
        }
        return null;
    }

    private InventoryBook create(Inventory inventory , Book book ,CreateQuantityModel createQuantityModel){
        InventoryBook inventoryBook = new InventoryBook();
        inventoryBook.setInventory(inventory);
        inventoryBook.setBook(book);
        inventoryBook.setSalePrice(createQuantityModel.getSalePrice());
        inventoryBook.setAvailableQuantity(createQuantityModel.getQuantity() + inventoryBook.getAvailableQuantity());

        inventoryBook = createInventoryBookUseCase.execute(inventoryBook);

        return inventoryBook;
    }

    private Book createNewBook(Book book){
        Book newBook = new Book();
        newBook.setImage(book.getImage());
        newBook.setTitle(book.getTitle());
        newBook.setPrice(book.getPrice());
        newBook.setCategory(book.getCategory());
        newBook.setChapter(book.getChapter());
        newBook.setIsInStock(false);
        newBook.setDistributorId(book.getDistributorId());

        return bookRepository.save(newBook).orElseThrow(
                () -> new CreateFailureException(String.format("Create new book '%s' failed", newBook.getTitle()))
        );
    }

}
