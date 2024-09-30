package com.booksms.distributor.common.Data.Repository;

import com.booksms.distributor.core.domain.entity.Distributor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistributorRepository extends JpaRepository<Distributor,Integer> {
    Optional<Distributor> findDistributorById(Integer id);
    Optional<Distributor> findDistributorByName(String name);
}
