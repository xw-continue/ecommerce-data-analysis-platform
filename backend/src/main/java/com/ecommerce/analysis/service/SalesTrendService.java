package com.ecommerce.analysis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ecommerce.analysis.entity.SalesTrend;
import java.util.List;

public interface SalesTrendService extends IService<SalesTrend> {
    List<SalesTrend> getSalesTrend();
}