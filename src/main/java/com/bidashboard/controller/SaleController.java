package com.bidashboard.controller;

import com.bidashboard.model.Sale;
import com.bidashboard.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "http://localhost:5173")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    // Create a sale
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sale createSale(@Valid @RequestBody Sale sale) {
        return saleService.createSale(sale);
    }

    // Get all sales
    @GetMapping
    public List<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    // Get sale by ID
    @GetMapping("/{id}")
    public Sale getSaleById(@PathVariable String id) {
        return saleService.getSaleById(id);
    }

    // Get sales by category
    @GetMapping("/category/{category}")
    public List<Sale> getSalesByCategory(@PathVariable String category) {
        return saleService.getSalesByCategory(category);
    }

    // Get sales by region
    @GetMapping("/region/{region}")
    public List<Sale> getSalesByRegion(@PathVariable String region) {
        return saleService.getSalesByRegion(region);
    }

    // Get sales by customer
    @GetMapping("/customer/{customerName}")
    public List<Sale> getSalesByCustomer(
            @PathVariable String customerName) {
        return saleService.getSalesByCustomer(customerName);
    }

    // Get sales between dates
    @GetMapping("/date-range")
    public List<Sale> getSalesBetweenDates(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return saleService.getSalesBetweenDates(startDate, endDate);
    }

    // Delete sale
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSale(@PathVariable String id) {
        saleService.deleteSale(id);
    }
}
