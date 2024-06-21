package com.zqh.gulimail.coupon.dao;

import com.zqh.gulimail.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author zqh
 * @email zqh@gmail.com
 * @date 2024-05-22 19:02:50
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
