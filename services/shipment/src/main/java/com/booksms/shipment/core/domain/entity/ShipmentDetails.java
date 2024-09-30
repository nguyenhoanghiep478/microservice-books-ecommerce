package com.booksms.shipment.core.domain.entity;

import com.booksms.shipment.core.domain.state.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShipmentDetails extends AbstractEntity{
    @Id
    private Integer id;

    @Column(nullable = false)
    private Integer originAddressId;

    @Column(nullable = false)
    private Integer destinationAddressId;

    @Column(nullable = false)
    private double distance;

    @Column(nullable = false)
    private Integer currentAddressId;

    @Column(nullable = false)
    private Double totalFee;

    @Column(nullable = false)
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "shipment_service_id")
    private ShipmentService shipmentService;
}
