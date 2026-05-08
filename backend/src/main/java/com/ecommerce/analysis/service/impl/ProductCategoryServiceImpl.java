package com.ecommerce.analysis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecommerce.analysis.entity.ProductCategory;
import com.ecommerce.analysis.mapper.ProductCategoryMapper;
import com.ecommerce.analysis.service.ProductCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Override
    public List<ProductCategory> getProductCategory() {
        return baseMapper.selectList(null);
    }
}