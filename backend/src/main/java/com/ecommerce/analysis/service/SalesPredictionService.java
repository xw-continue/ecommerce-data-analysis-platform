package com.ecommerce.analysis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ecommerce.analysis.entity.SalesPrediction;
import java.util.List;

public interface SalesPredictionService extends IService<SalesPrediction> {
    List<SalesPrediction> getSalesPrediction();
}