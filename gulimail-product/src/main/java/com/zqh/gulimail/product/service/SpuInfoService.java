package com.zqh.gulimail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zqh.common.utils.PageUtils;
import com.zqh.gulimail.product.entity.SpuInfoDescEntity;
import com.zqh.gulimail.product.entity.SpuInfoEntity;
import com.zqh.gulimail.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author zqh
 * @email zqh@gmail.com
 * @date 2024-05-22 00:39:17
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo vo);

    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);
}

