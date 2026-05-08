package com.ecommerce.analysis.controller;

import com.ecommerce.analysis.entity.RegionAnalysis;
import com.ecommerce.analysis.service.RegionAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/region-analysis")
public class RegionAnalysisController {

    @Autowired
    private RegionAnalysisService regionAnalysisService;

    @GetMapping
    public List<RegionAnalysis> getRegionAnalysis() {
        return regionAnalysisService.getRegionAnalysis();
    }
}