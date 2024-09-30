package com.booksms.shipment.application.usecase;

import com.booksms.shipment.application.model.ShipmentModel;
import com.booksms.shipment.application.servicegateway.IMapService;
import com.booksms.shipment.core.domain.entity.ShipmentDetails;
import com.booksms.shipment.core.domain.entity.ShipmentService;
import com.booksms.shipment.core.domain.exception.CreateFailureException;
import com.booksms.shipment.core.domain.exception.ServiceNotExistedException;
import com.booksms.shipment.core.domain.repository.IShipmentRepository;
import com.booksms.shipment.core.domain.repository.IShipmentServiceRepository;
import com.booksms.shipment.core.domain.state.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

import static com.booksms.shipment.core.domain.state.Status.PENDING;
import static com.booksms.shipment.core.domain.state.Status.SHIPPED;

@Component
@RequiredArgsConstructor
public class CreateShipmentUseCase {
    private final IShipmentRepository shipmentRepository;
    private final IShipmentServiceRepository shipmentServiceRepository;
    private final MapperUseCase mapperUseCase;
    private final IMapService mapService;

    public ShipmentModel execute(ShipmentModel model){
        ShipmentService shipmentService = shipmentServiceRepository.findById(model.getShipmentServiceId())
                .orElseThrow(() -> new ServiceNotExistedException("Invalid shipment service"));

        model.setStatus(PENDING);

        Random random = new Random();
        if(model.getTrackingNumber() == null){
            String trackingNumber = String.valueOf((random.nextInt() * 1000000000 +1));

            model.setTrackingNumber(trackingNumber);
        }

        model.setTotalFee(model.getDistance() * shipmentService.getCostPerKm(model.getDistance()));

        ShipmentDetails entity = mapperUseCase.toShipmentDetails(model, shipmentService);

        entity = shipmentRepository.save(entity);

        if(entity == null){
            throw new CreateFailureException("create shipment failed");
        }

        return model;
    }
}
