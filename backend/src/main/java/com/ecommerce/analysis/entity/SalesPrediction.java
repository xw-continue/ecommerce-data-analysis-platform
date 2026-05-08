package com.ecommerce.analysis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("sales_prediction")
public class SalesPrediction {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Date date;
    private BigDecimal predictedSales;
    private Integer predictedQuantity;
    private BigDecimal confidence;
}