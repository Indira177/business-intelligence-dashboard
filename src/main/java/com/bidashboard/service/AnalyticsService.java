
package com.bidashboard.service;

import com.bidashboard.model.Sale;
import com.bidashboard.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    private final SaleRepository saleRepository;

    public AnalyticsService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    // Calculate overall business analytics
    public Map<String, Object> getSalesSummary() {

        List<Sale> sales = saleRepository.findAll();

        double totalRevenue = sales.stream()
                .mapToDouble(Sale::getTotalAmount)
                .sum();

        int totalOrders = sales.size();

        int totalUnitsSold = sales.stream()
                .mapToInt(Sale::getQuantity)
                .sum();

        double averageOrderValue = totalOrders > 0
                ? totalRevenue / totalOrders
                : 0.0;

        Map<String, Object> summary = new LinkedHashMap<>();

        summary.put("totalRevenue", totalRevenue);
        summary.put("totalOrders", totalOrders);
        summary.put("totalUnitsSold", totalUnitsSold);
        summary.put("averageOrderValue", averageOrderValue);

        return summary;
    }

    // Calculate revenue by category
    public Map<String, Double> getRevenueByCategory() {

        List<Sale> sales = saleRepository.findAll();

        Map<String, Double> revenueByCategory = new LinkedHashMap<>();

        for (Sale sale : sales) {

            String category = sale.getCategory();

            if (category == null || category.isBlank()) {
                category = "Unknown";
            }

            revenueByCategory.merge(
                    category,
                    sale.getTotalAmount(),
                    Double::sum
            );
        }

        return revenueByCategory;
    }

    // Calculate revenue by region
    public Map<String, Double> getRevenueByRegion() {

        List<Sale> sales = saleRepository.findAll();

        Map<String, Double> revenueByRegion = new LinkedHashMap<>();

        for (Sale sale : sales) {

            String region = sale.getRegion();

            if (region == null || region.isBlank()) {
                region = "Unknown";
            }

            revenueByRegion.merge(
                    region,
                    sale.getTotalAmount(),
                    Double::sum
            );
        }

        return revenueByRegion;
    }

    // Calculate overall profit summary
    public Map<String, Object> getProfitSummary() {

        List<Sale> sales = saleRepository.findAll();

        double totalRevenue = 0;
        double totalCost = 0;

        for (Sale sale : sales) {
            totalRevenue += sale.getTotalAmount();
            totalCost += sale.getQuantity() * sale.getCostPrice();
        }

        double grossProfit = totalRevenue - totalCost;

        double profitMargin = totalRevenue > 0
                ? (grossProfit / totalRevenue) * 100
                : 0.0;

        Map<String, Object> summary = new LinkedHashMap<>();

        summary.put("totalRevenue", totalRevenue);
        summary.put("totalCost", totalCost);
        summary.put("grossProfit", grossProfit);
        summary.put("profitMarginPercentage", profitMargin);

        return summary;
    }

    // Calculate profit by category
    public Map<String, Double> getProfitByCategory() {

        List<Sale> sales = saleRepository.findAll();

        Map<String, Double> profitByCategory = new LinkedHashMap<>();

        for (Sale sale : sales) {

            String category = sale.getCategory();

            if (category == null || category.isBlank()) {
                category = "Unknown";
            }

            double profit = sale.getTotalAmount()
                    - (sale.getQuantity() * sale.getCostPrice());

            profitByCategory.merge(
                    category,
                    profit,
                    Double::sum
            );
        }

        return profitByCategory;
    }
}
