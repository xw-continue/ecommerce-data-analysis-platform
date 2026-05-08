package com.ecommerce.analysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecommerce.analysis.entity.SalesData;
import com.ecommerce.analysis.mapper.SalesDataMapper;
import com.ecommerce.analysis.service.SalesDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesDataServiceImpl extends ServiceImpl<SalesDataMapper, SalesData> implements SalesDataService {

    @Override
    public List<SalesData> getSalesData(int page, int size) {
        int offset = (page - 1) * size;
        return baseMapper.selectList(null).subList(offset, Math.min(offset + size, baseMapper.selectList(null).size()));
    }

    @Override
    public List<SalesData> searchSalesData(String keyword, int page, int size) {
        QueryWrapper<SalesData> wrapper = new QueryWrapper<>();
        wrapper.like("product_name", keyword)
                .or().like("category", keyword)
                .or().like("region", keyword)
                .or().like("order_id", keyword);
        
        List<SalesData> list = baseMapper.selectList(wrapper);
        int offset = (page - 1) * size;
        return list.subList(offset, Math.min(offset + size, list.size()));
    }

    @Override
    public long getTotalCount() {
        return baseMapper.selectCount(null);
    }

    @Override
    public long getSearchCount(String keyword) {
        QueryWrapper<SalesData> wrapper = new QueryWrapper<>();
        wrapper.like("product_name", keyword)
                .or().like("category", keyword)
                .or().like("region", keyword)
                .or().like("order_id", keyword);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public List<SalesData> advancedSearch(String keyword, String startDate, String endDate, Double minPrice, Double maxPrice, Integer minQuantity, Integer maxQuantity, String category, String region, int page, int size) {
        QueryWrapper<SalesData> wrapper = new QueryWrapper<>();
        
        // 关键词搜索
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like("product_name", keyword)
                    .or().like("category", keyword)
                    .or().like("region", keyword)
                    .or().like("order_id", keyword);
        }
        
        // 日期范围
        if (startDate != null && !startDate.isEmpty()) {
            wrapper.ge("order_date", startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            wrapper.le("order_date", endDate);
        }
        
        // 价格范围
        if (minPrice != null) {
            wrapper.ge("price", minPrice);
        }
        if (maxPrice != null) {
            wrapper.le("price", maxPrice);
        }
        
        // 销量范围
        if (minQuantity != null) {
            wrapper.ge("quantity", minQuantity);
        }
        if (maxQuantity != null) {
            wrapper.le("quantity", maxQuantity);
        }
        
        // 分类
        if (category != null && !category.isEmpty()) {
            wrapper.eq("category", category);
        }
        
        // 地区
        if (region != null && !region.isEmpty()) {
            wrapper.eq("region", region);
        }
        
        List<SalesData> list = baseMapper.selectList(wrapper);
        int offset = (page - 1) * size;
        return list.subList(offset, Math.min(offset + size, list.size()));
    }

    @Override
    public long getAdvancedSearchCount(String keyword, String startDate, String endDate, Double minPrice, Double maxPrice, Integer minQuantity, Integer maxQuantity, String category, String region) {
        QueryWrapper<SalesData> wrapper = new QueryWrapper<>();
        
        // 关键词搜索
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like("product_name", keyword)
                    .or().like("category", keyword)
                    .or().like("region", keyword)
                    .or().like("order_id", keyword);
        }
        
        // 日期范围
        if (startDate != null && !startDate.isEmpty()) {
            wrapper.ge("order_date", startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            wrapper.le("order_date", endDate);
        }
        
        // 价格范围
        if (minPrice != null) {
            wrapper.ge("price", minPrice);
        }
        if (maxPrice != null) {
            wrapper.le("price", maxPrice);
        }
        
        // 销量范围
        if (minQuantity != null) {
            wrapper.ge("quantity", minQuantity);
        }
        if (maxQuantity != null) {
            wrapper.le("quantity", maxQuantity);
        }
        
        // 分类
        if (category != null && !category.isEmpty()) {
            wrapper.eq("category", category);
        }
        
        // 地区
        if (region != null && !region.isEmpty()) {
            wrapper.eq("region", region);
        }
        
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public List<SalesData> getSalesDataByDateRange(String startDate, String endDate) {
        QueryWrapper<SalesData> wrapper = new QueryWrapper<>();
        
        if (startDate != null && !startDate.isEmpty()) {
            wrapper.ge("order_date", startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            wrapper.le("order_date", endDate);
        }
        
        return baseMapper.selectList(wrapper);
    }
}