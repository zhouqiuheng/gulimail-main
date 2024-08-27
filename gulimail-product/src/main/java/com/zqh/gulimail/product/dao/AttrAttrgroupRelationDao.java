package com.zqh.gulimail.product.dao;

import com.zqh.gulimail.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 * 
 * @author zqh
 * @email zqh@gmail.com
 * @date 2024-05-22 00:39:17
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    void deleteBatchRelation(@Param("entites") List<AttrAttrgroupRelationEntity> entites);
}
