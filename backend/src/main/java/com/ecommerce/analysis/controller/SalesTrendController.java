package com.ecommerce.analysis.controller;

import com.ecommerce.analysis.entity.SalesTrend;
import com.ecommerce.analysis.service.SalesTrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sales-trend")
public class SalesTrendController {

    @Autowired
    private SalesTrendService salesTrendService;

    @GetMapping
    public List<SalesTrend> getSalesTrend() {
        return salesTrendService.getSalesTrend();
    }
}