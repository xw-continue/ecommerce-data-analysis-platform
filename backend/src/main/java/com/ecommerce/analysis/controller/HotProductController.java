package com.ecommerce.analysis.controller;

import com.ecommerce.analysis.entity.HotProduct;
import com.ecommerce.analysis.service.HotProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hot-products")
public class HotProductController {

    @Autowired
    private HotProductService hotProductService;

    @GetMapping
    public List<HotProduct> getHotProducts() {
        return hotProductService.getHotProducts();
    }
}