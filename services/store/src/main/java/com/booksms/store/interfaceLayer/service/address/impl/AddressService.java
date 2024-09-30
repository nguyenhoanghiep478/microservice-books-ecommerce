package com.booksms.store.interfaceLayer.service.address.impl;

import com.booksms.store.application.model.AddressModel;
import com.booksms.store.application.model.OrdersModel;
import com.booksms.store.application.model.ShipmentModel;
import com.booksms.store.interfaceLayer.DTO.Request.CreateAddressDTO;
import com.booksms.store.interfaceLayer.service.address.IAddressService;
import com.booksms.store.interfaceLayer.service.address.ICreateAddressService;
import com.booksms.store.interfaceLayer.service.address.IFindAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService implements IAddressService {
    private final ICreateAddressService createAddressService;
    private final IFindAddressService findAddressService;
    private final KafkaTemplate<String, ShipmentModel> kafkaTemplate;
    private final ModelMapper modelMapper;


    @KafkaListener(id = "create-shipment",topics = "order-created")
    public void createShipment(OrdersModel order) {


        AddressModel model = createAddress(CreateAddressDTO.builder()
                .address(order.getDestinationAddress())
                .build());

        ShipmentModel shipmentModel = ShipmentModel.builder()
                .distance(order.getDistance())
                .destinationAddressId(model.getId())
                .currentAddressId(order.getOriginAddressId())
                .originAddressId(order.getOriginAddressId())
                .shipmentServiceId(order.getShipmentServiceId())
                .id(order.getShipmentId())
                .trackingNumber(getTrackingNumber())
                .totalFee(order.getShipmentFee())
                .build();
        log.info("created shipment {}", shipmentModel);
        kafkaTemplate.send("create-shipment",shipmentModel);

    }



    private String getTrackingNumber() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder trackingNumber = new StringBuilder();
        Random random = new Random();

        int length = 12;

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            trackingNumber.append(characters.charAt(index));
        }

        return trackingNumber.toString();
    }

    @Override
    public List<String> findByIds(List<Integer> ids) {
        return findAddressService.findByids(ids);
    }

    @Override
    public AddressModel createAddress(CreateAddressDTO address) {
        AddressModel addressModel = new AddressModel();
        String[] fieldAddress = address.getAddress().split(",");
        addressModel.setStreet(fieldAddress[0]);
        addressModel.setState(fieldAddress[1]);
        addressModel.setCity(fieldAddress[2]);
        addressModel.setZip("700000");

        return createAddressService.createAddress(addressModel);
    }
}
