package com.zqh.gulimail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zqh.common.utils.PageUtils;
import com.zqh.gulimail.product.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author zqh
 * @email zqh@gmail.com
 * @date 2024-05-22 00:39:17
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

