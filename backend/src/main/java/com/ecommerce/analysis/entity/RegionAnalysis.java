package com.ecommerce.analysis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("region_analysis")
public class RegionAnalysis {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String region;
    private BigDecimal totalSales;
    private Integer totalQuantity;
    private Integer orderCount;
    private BigDecimal salesPercentage;
}