package com.booksms.shipment.core.domain.state;

import lombok.Getter;

@Getter
public enum ShipmentMethod {
    FAST_DELIVER(50,5),
    STANDARD_DELIVER(40,3);

    final int speed ;
    final int costPerKm;

    ShipmentMethod(int speed,int costPerKm) {
        this.speed = speed;
        this.costPerKm = costPerKm;
    }


}
