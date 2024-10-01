package com.booksms.store.application.usecase.inventory.UpdateUseCase;

import com.booksms.store.application.model.BookModel;
import com.booksms.store.application.model.CreateQuantityModel;
import com.booksms.store.application.model.StockInModel;
import com.booksms.store.application.usecase.Book.CreateUseCase.CreateBookUseCase;
import com.booksms.store.application.usecase.Book.FindUseCase.impl.FindBooksUseCase;
import com.booksms.store.application.usecase.inventory.CreateUseCase.CreateInventoryUseCase;
import com.booksms.store.application.usecase.inventory.CreateUseCase.CreateQuantityUseCase;
import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.core.domain.entity.Inventory;
import com.booksms.store.core.domain.entity.InventoryBook;
import com.booksms.store.core.domain.exception.InventoryNotExistedException;
import com.booksms.store.core.domain.repository.IInventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockInUseCase {
    private final IInventoryRepository inventoryRepository;
    private final CreateBookUseCase createBookUseCase;
    private final CreateQuantityUseCase createQuantityUseCase;
    private final FindBooksUseCase findBooksUseCase;
    private final CreateInventoryUseCase createInventoryUseCase;

    @Transactional(rollbackOn = Exception.class)
    public void execute(StockInModel model) {
        Inventory inventory = inventoryRepository.findById(model.getInventoryId()).orElseThrow(
                () -> new InventoryNotExistedException("inventory not found")
        );

        Book updateBook = findBooksUseCase.findById(model.getBookId());



        InventoryBook inventoryBook = inventory.getInventoryBookHaveBookId(updateBook.getId());

        //not have inventory book yet
        if(inventoryBook == null) {
            updateBook.setIsInStock(true);
            updateBook.setPrice(model.getPurchasePrice());
            inventoryBook = new InventoryBook(inventory,updateBook,model.getQuantity());
            inventoryBook.setSalePrice(model.getSalePrice());
            inventory.getInventoryBooks().add(inventoryBook);
            inventoryRepository.save(inventory);
            return;
        }


        if(inventoryBook.getSalePrice().stripTrailingZeros().equals(model.getSalePrice().stripTrailingZeros())){
            int currentQuantity = inventoryBook.getAvailableQuantity();
            inventoryBook.setAvailableQuantity(currentQuantity + model.getQuantity());
        }else{
            // different price in inventory
            Book book = inventoryBook.getBook();
            BookModel newBook = BookModel.builder()
                    .title(book.getTitle())
                    .categoryId(book.getCategory().getId())
                    .chapter(book.getChapter())
                    .pathImage(book.getImage())
                    .distributorId(book.getDistributorId())
                    .name(book.getName())
                        .inventoryId(model.getInventoryId())
                    .isInStock(false)
                    .build();
            Book newBookInStock = createBookUseCase.execute(newBook);

            createQuantityUseCase.execute(CreateQuantityModel.builder()
                            .bookId(newBookInStock.getId())
                            .inventoryId(model.getInventoryId())
                            .purchasePrice(model.getPurchasePrice())
                            .salePrice(model.getSalePrice())
                            .quantity(model.getQuantity())
                    .build());
        }


    }
}
