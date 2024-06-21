package com.zqh.gulimail.coupon.dao;

import com.zqh.gulimail.coupon.entity.CouponHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 * 
 * @author zqh
 * @email zqh@gmail.com
 * @date 2024-05-22 19:02:50
 */
@Mapper
public interface CouponHistoryDao extends BaseMapper<CouponHistoryEntity> {
	
}
