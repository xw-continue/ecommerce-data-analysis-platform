package com.ecommerce.analysis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecommerce.analysis.entity.RegionAnalysis;
import com.ecommerce.analysis.mapper.RegionAnalysisMapper;
import com.ecommerce.analysis.service.RegionAnalysisService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionAnalysisServiceImpl extends ServiceImpl<RegionAnalysisMapper, RegionAnalysis> implements RegionAnalysisService {

    @Override
    public List<RegionAnalysis> getRegionAnalysis() {
        return baseMapper.selectList(null);
    }
}