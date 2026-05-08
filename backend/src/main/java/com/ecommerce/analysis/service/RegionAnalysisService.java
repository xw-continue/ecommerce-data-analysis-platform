package com.ecommerce.analysis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ecommerce.analysis.entity.RegionAnalysis;
import java.util.List;

public interface RegionAnalysisService extends IService<RegionAnalysis> {
    List<RegionAnalysis> getRegionAnalysis();
}