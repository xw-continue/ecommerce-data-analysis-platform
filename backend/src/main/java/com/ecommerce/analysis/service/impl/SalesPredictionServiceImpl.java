package com.ecommerce.analysis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecommerce.analysis.entity.SalesPrediction;
import com.ecommerce.analysis.mapper.SalesPredictionMapper;
import com.ecommerce.analysis.service.SalesPredictionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesPredictionServiceImpl extends ServiceImpl<SalesPredictionMapper, SalesPrediction> implements SalesPredictionService {

    @Override
    public List<SalesPrediction> getSalesPrediction() {
        return baseMapper.selectList(null);
    }
}