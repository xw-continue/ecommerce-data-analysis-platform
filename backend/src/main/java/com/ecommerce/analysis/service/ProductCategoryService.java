package com.ecommerce.analysis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ecommerce.analysis.entity.ProductCategory;
import java.util.List;

public interface ProductCategoryService extends IService<ProductCategory> {
    List<ProductCategory> getProductCategory();
}