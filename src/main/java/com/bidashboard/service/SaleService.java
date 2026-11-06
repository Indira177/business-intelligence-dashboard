package com.bidashboard.service;

import com.bidashboard.exception.ResourceNotFoundException;
import com.bidashboard.model.Sale;
import com.bidashboard.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    // Create a new sale
    public Sale createSale(Sale sale) {
        validateSale(sale);

        sale.setTotalAmount(sale.getQuantity() * sale.getUnitPrice());

        return saleRepository.save(sale);
    }

    // Get all sales
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    // Get sale by ID
    public Sale getSaleById(String id) {
        return saleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Sale not found with ID: " + id
                        ));
    }

    // Get sales by category
    public List<Sale> getSalesByCategory(String category) {
        return saleRepository.findByCategory(category);
    }

    // Get sales by region
    public List<Sale> getSalesByRegion(String region) {
        return saleRepository.findByRegion(region);
    }

    // Get sales by customer
    public List<Sale> getSalesByCustomer(String customerName) {
        return saleRepository.findByCustomerName(customerName);
    }

    // Get sales between dates
    public List<Sale> getSalesBetweenDates(
            LocalDateTime startDate,
            LocalDateTime endDate) {

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    "Start date cannot be after end date"
            );
        }

        return saleRepository.findBySaleDateBetween(startDate, endDate);
    }

    // Delete sale
    public void deleteSale(String id) {
        if (!saleRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Sale not found with ID: " + id
            );
        }

        saleRepository.deleteById(id);
    }

    // Validate sale details
    private void validateSale(Sale sale) {

        if (sale.getQuantity() <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero"
            );
        }

        if (sale.getUnitPrice() < 0 || sale.getCostPrice() < 0) {
            throw new IllegalArgumentException(
                    "Unit price and cost price cannot be negative"
            );
        }
    }
}
