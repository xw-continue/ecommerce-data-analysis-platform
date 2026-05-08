package com.ecommerce.analysis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ecommerce.analysis.entity.HotProduct;
import java.util.List;

public interface HotProductService extends IService<HotProduct> {
    List<HotProduct> getHotProducts();
}