package com.zqh.gulimail.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zqh.common.utils.PageUtils;
import com.zqh.gulimail.member.entity.MemberStatisticsInfoEntity;

import java.util.Map;

/**
 * 会员统计信息
 *
 * @author zqh
 * @email zqh@gmail.com
 * @date 2024-05-22 19:58:26
 */
public interface MemberStatisticsInfoService extends IService<MemberStatisticsInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

