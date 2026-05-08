package com.ecommerce.analysis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecommerce.analysis.entity.SalesTrend;
import com.ecommerce.analysis.mapper.SalesTrendMapper;
import com.ecommerce.analysis.service.SalesTrendService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesTrendServiceImpl extends ServiceImpl<SalesTrendMapper, SalesTrend> implements SalesTrendService {

    @Override
    public List<SalesTrend> getSalesTrend() {
        return baseMapper.selectList(null);
    }
}