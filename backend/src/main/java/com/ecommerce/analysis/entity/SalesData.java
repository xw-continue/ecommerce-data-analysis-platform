package com.ecommerce.analysis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("sales_data")
public class SalesData {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderId;
    private String userId;
    private String productId;
    private String productName;
    private String category;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal sales;
    private String region;
    private Date orderDate;
}