package com.booksms.shipment.application.usecase;

import com.booksms.shipment.application.model.Criteria;
import com.booksms.shipment.core.domain.entity.ShipmentDetails;
import com.booksms.shipment.core.domain.repository.IShipmentDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindShipmentDetailsUseCase {

    private final IShipmentDetailRepository shipmentDetailRepository;

    public List<ShipmentDetails> execute(List<Criteria> criteriaList) {
        if(criteriaList == null || criteriaList.isEmpty()) {
            return shipmentDetailRepository.findAll();
        }
        return shipmentDetailRepository.findByCriteria(criteriaList);
    }
}
