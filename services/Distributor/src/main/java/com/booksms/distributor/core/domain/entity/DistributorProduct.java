package com.booksms.distributor.core.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DistributorProduct {
    @EmbeddedId
    private DistributorProductId id;

    @ManyToOne
    @MapsId("distributorId")
    @JoinColumn(name = "distributor_id")
    private Distributor distributor;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

}
