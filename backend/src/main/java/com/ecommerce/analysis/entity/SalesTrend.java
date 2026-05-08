package com.ecommerce.analysis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("sales_trend")
public class SalesTrend {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Date date;
    private BigDecimal totalSales;
    private Integer totalQuantity;
    private Integer orderCount;
}