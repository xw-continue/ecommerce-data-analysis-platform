package com.ecommerce.analysis.controller;

import com.ecommerce.analysis.entity.ProductCategory;
import com.ecommerce.analysis.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product-category")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping
    public List<ProductCategory> getProductCategory() {
        return productCategoryService.getProductCategory();
    }
}