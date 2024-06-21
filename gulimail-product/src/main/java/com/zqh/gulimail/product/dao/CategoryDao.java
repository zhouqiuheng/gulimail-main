package com.zqh.gulimail.product.dao;

import com.zqh.gulimail.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author zqh
 * @email zqh@gmail.com
 * @date 2024-05-22 00:39:17
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
