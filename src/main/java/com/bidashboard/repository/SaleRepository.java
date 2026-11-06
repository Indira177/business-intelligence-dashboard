package com.bidashboard.repository;

import com.bidashboard.model.Sale;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends MongoRepository<Sale, String> {

    List<Sale> findByCategory(String category);

    List<Sale> findByRegion(String region);

    List<Sale> findByCustomerName(String customerName);

    List<Sale> findBySaleDateBetween(
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
