package com.booksms.shipment.infrastructure.repository;

import com.booksms.shipment.application.model.Criteria;
import com.booksms.shipment.core.domain.entity.ShipmentDetails;
import com.booksms.shipment.core.domain.repository.IShipmentDetailRepository;
import com.booksms.shipment.infrastructure.jpa.ShipmentDetailJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class ShipmentDetailRepository extends AbstractRepository<ShipmentDetails> implements IShipmentDetailRepository {
    private final ShipmentDetailJpaRepository shipmentDetailJpaRepository;

    ShipmentDetailRepository(Class<ShipmentDetails> entityClass, ShipmentDetailJpaRepository shipmentDetailJpaRepository) {
        super(entityClass);
        this.shipmentDetailJpaRepository = shipmentDetailJpaRepository;
    }

    @Override
    public List<ShipmentDetails> findAll() {
        return shipmentDetailJpaRepository.findAll();
    }

    @Override
    public List<ShipmentDetails> findByCriteria(List<Criteria> criteriaList) {
        return abstractSearch(criteriaList);
    }

    @Override
    public ShipmentDetails save(ShipmentDetails mergedShipmentDetail) {
        return shipmentDetailJpaRepository.save(mergedShipmentDetail);
    }
}
