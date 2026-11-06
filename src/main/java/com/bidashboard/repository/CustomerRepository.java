package com.bidashboard.repository;

import com.bidashboard.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    Optional<Customer> findByEmail(String email);

    List<Customer> findByCustomerNameContainingIgnoreCase(String customerName);

    List<Customer> findByCityIgnoreCase(String city);

    List<Customer> findByRegionIgnoreCase(String region);

    boolean existsByEmail(String email);
}
