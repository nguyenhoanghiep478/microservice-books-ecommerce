package com.booksms.shipment.core.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class ShipmentService {
    @Id
    private Integer id;

    private String name;

    private int speed;

    private float costPerKm;

    @OneToMany(mappedBy = "shipmentService",cascade = CascadeType.ALL)
    private List<ShipmentDetails> shipmentDetails;

    public float getCostPerKm(Double distance) {
        if(distance < 5){
            return costPerKm;
        }
        return (float) (costPerKm * 0.80);
    }
}
