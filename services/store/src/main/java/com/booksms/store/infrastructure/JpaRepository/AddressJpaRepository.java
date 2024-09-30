package com.booksms.store.infrastructure.JpaRepository;

import com.booksms.store.core.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJpaRepository extends JpaRepository<Address, Integer> {
}
