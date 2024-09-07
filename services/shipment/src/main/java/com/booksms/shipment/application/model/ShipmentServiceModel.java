package com.booksms.shipment.application.model;

import com.booksms.shipment.core.domain.entity.ShipmentDetails;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;
@Data
public class ShipmentServiceModel {
    @Id
    private Integer id;

    private String name;

    private int speed;

    private float costPerKm;
}
