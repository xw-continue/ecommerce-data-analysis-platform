package com.ecommerce.analysis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecommerce.analysis.entity.HotProduct;
import com.ecommerce.analysis.mapper.HotProductMapper;
import com.ecommerce.analysis.service.HotProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotProductServiceImpl extends ServiceImpl<HotProductMapper, HotProduct> implements HotProductService {

    @Override
    public List<HotProduct> getHotProducts() {
        return baseMapper.selectList(null);
    }
}