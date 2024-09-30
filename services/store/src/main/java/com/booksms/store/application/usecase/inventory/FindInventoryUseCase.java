package com.booksms.store.application.usecase.inventory;

import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.core.domain.entity.Inventory;
import com.booksms.store.core.domain.repository.IInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindInventoryUseCase {
    private final IInventoryRepository inventoryRepository;

    public List<Inventory> execute(List<SearchCriteria> criteriaList) {
        return inventoryRepository.findByCriteriaList(criteriaList);
    }
}
