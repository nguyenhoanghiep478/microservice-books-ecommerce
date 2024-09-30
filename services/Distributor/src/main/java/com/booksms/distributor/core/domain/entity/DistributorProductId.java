package com.booksms.distributor.core.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@Getter
@Setter
public class DistributorProductId {
    private Integer productId;
    private Integer distributorId;

    public DistributorProductId(Integer productId, Integer distributorId) {
        this.productId = productId;
        this.distributorId = distributorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistributorProductId that = (DistributorProductId) o;
        return Objects.equals(distributorId, that.distributorId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, distributorId);
    }
    public DistributorProductId() {

    }
}
