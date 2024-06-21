package com.zqh.gulimail.order.dao;

import com.zqh.gulimail.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author zqh
 * @email zqh@gmail.com
 * @date 2024-05-22 20:12:12
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
