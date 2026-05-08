package com.ecommerce.analysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecommerce.analysis.entity.HotProduct;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotProductMapper extends BaseMapper<HotProduct> {
}