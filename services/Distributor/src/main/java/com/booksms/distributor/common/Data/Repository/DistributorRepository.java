package com.booksms.distributor.common.Data.Repository;

import com.booksms.distributor.common.Data.Entity.Distributor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistributorRepository extends JpaRepository<Distributor,Integer> {
    Optional<Distributor> findDistributorById(Integer id);
    Optional<Distributor> findDistributorByName(String name);
}
