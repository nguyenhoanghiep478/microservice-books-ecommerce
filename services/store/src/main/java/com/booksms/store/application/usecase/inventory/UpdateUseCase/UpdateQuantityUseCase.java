package com.booksms.store.application.usecase.inventory.UpdateUseCase;

import com.booksms.store.application.model.SellProductModel;
import com.booksms.store.application.model.StockInModel;
import com.booksms.store.application.model.UpdateQuantityModel;
import com.booksms.store.core.domain.entity.Inventory;
import com.booksms.store.core.domain.entity.InventoryBook;
import com.booksms.store.core.domain.exception.BookExpcetion.BookNotFoundException;
import com.booksms.store.core.domain.exception.InSufficientQuantityException;
import com.booksms.store.core.domain.exception.InventoryNotExistedException;
import com.booksms.store.core.domain.repository.IInventoryRepository;
import com.booksms.store.interfaceLayer.DTO.Request.OrderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UpdateQuantityUseCase {
    private final SellProductUseCase sellProductUseCase;
    private final StockInUseCase stockInUseCase;

    @Transactional(rollbackFor = Exception.class)
    public void execute(UpdateQuantityModel updateQuantityModel) {

       if(updateQuantityModel.getOrderType() == null || updateQuantityModel.getOrderType().equals(OrderType.SELL)){
           sellProductUseCase.execute(SellProductModel.builder()
                           .quantity(updateQuantityModel.getAddOrMinusQuantity())
                           .bookId(updateQuantityModel.getBookId())
                           .inventoryId(updateQuantityModel.getInventoryId())
                   .build());
       }else{
           stockInUseCase.execute(StockInModel.builder()
                           .quantity(updateQuantityModel.getAddOrMinusQuantity())
                           .bookId(updateQuantityModel.getBookId())
                           .inventoryId(updateQuantityModel.getInventoryId())
                           .salePrice(updateQuantityModel.getSalePrice())
                           .purchasePrice(updateQuantityModel.getPurchasePrice())
                   .build());
       }
    }

}
