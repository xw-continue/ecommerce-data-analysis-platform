package com.ecommerce.analysis.controller;

import com.ecommerce.analysis.entity.SalesPrediction;
import com.ecommerce.analysis.service.SalesPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sales-prediction")
public class SalesPredictionController {

    @Autowired
    private SalesPredictionService salesPredictionService;

    @GetMapping
    public List<SalesPrediction> getSalesPrediction() {
        return salesPredictionService.getSalesPrediction();
    }
}