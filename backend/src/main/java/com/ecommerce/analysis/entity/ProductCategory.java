package com.ecommerce.analysis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("product_category")
public class ProductCategory {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String category;
    private BigDecimal totalSales;
    private Integer totalQuantity;
    private Integer orderCount;
    private BigDecimal salesPercentage;
}