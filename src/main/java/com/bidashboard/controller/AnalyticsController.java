package com.bidashboard.controller;

import com.bidashboard.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    // Get overall sales summary
    @GetMapping("/summary")
    public Map<String, Object> getSalesSummary() {
        return analyticsService.getSalesSummary();
    }

    // Get revenue by category
    @GetMapping("/revenue-by-category")
    public Map<String, Double> getRevenueByCategory() {
        return analyticsService.getRevenueByCategory();
    }


    // Get overall profit summary
    @GetMapping("/profit-summary")
    public Map<String, Object> getProfitSummary() {
        return analyticsService.getProfitSummary();
    }

    // Get profit by category
    @GetMapping("/profit-by-category")
    public Map<String, Double> getProfitByCategory() {
        return analyticsService.getProfitByCategory();
    }

    // Get revenue by region
    @GetMapping("/revenue-by-region")
    public Map<String, Double> getRevenueByRegion() {
        return analyticsService.getRevenueByRegion();
    }
}
