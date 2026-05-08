package com.ecommerce.analysis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("hot_products")
public class HotProduct {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String productId;
    private String productName;
    private BigDecimal totalSales;
    private Integer totalQuantity;
    private Integer orderCount;
}