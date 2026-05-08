package com.ecommerce.analysis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ecommerce.analysis.entity.SalesData;
import java.util.List;

public interface SalesDataService extends IService<SalesData> {
    List<SalesData> getSalesData(int page, int size);
    List<SalesData> searchSalesData(String keyword, int page, int size);
    long getTotalCount();
    long getSearchCount(String keyword);
    List<SalesData> advancedSearch(String keyword, String startDate, String endDate, Double minPrice, Double maxPrice, Integer minQuantity, Integer maxQuantity, String category, String region, int page, int size);
    long getAdvancedSearchCount(String keyword, String startDate, String endDate, Double minPrice, Double maxPrice, Integer minQuantity, Integer maxQuantity, String category, String region);
    List<SalesData> getSalesDataByDateRange(String startDate, String endDate);
}