package com.booksms.store.interfaceLayer.service.address;

import java.util.List;

public interface IFindAddressService {
    List<String> findByids(List<Integer> ids);
}
