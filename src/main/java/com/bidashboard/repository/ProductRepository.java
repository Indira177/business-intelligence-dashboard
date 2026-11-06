package com.bidashboard.repository;

import com.bidashboard.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByProductNameContainingIgnoreCase(String productName);

    List<Product> findByCategoryIgnoreCase(String category);

    List<Product> findBySupplierIgnoreCase(String supplier);

    List<Product> findByStockQuantityLessThan(int quantity);
}
