package com.booksms.shipment.infrastructure.serviceGateway;

import java.util.HashMap;
import java.util.List;

public interface IAddressService {
    HashMap<Integer,String> findByIds(List<Integer> destinationIds);

    Integer createAdress(String destinationAddress);
}
