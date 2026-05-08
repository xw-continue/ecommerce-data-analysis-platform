package com.ecommerce.analysis.controller;

import com.ecommerce.analysis.entity.SalesData;
import com.ecommerce.analysis.service.SalesDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sales-data")
public class SalesDataController {

    @Autowired
    private SalesDataService salesDataService;

    @GetMapping("/list")
    public Map<String, Object> getSalesData(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        List<SalesData> list = salesDataService.getSalesData(page, size);
        long total = salesDataService.getTotalCount();
        return Map.of("list", list, "total", total);
    }

    @GetMapping("/search")
    public Map<String, Object> searchSalesData(@RequestParam String keyword, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        List<SalesData> list = salesDataService.searchSalesData(keyword, page, size);
        long total = salesDataService.getSearchCount(keyword);
        return Map.of("list", list, "total", total);
    }

    @GetMapping("/advanced-search")
    public Map<String, Object> advancedSearch(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minQuantity,
            @RequestParam(required = false) Integer maxQuantity,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String region,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        List<SalesData> list = salesDataService.advancedSearch(
                keyword, startDate, endDate, minPrice, maxPrice, 
                minQuantity, maxQuantity, category, region, page, size);
        long total = salesDataService.getAdvancedSearchCount(
                keyword, startDate, endDate, minPrice, maxPrice, 
                minQuantity, maxQuantity, category, region);
        
        return Map.of("list", list, "total", total);
    }
}