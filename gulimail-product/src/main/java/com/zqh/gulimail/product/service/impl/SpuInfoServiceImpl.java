package com.zqh.gulimail.product.service.impl;

import com.zqh.gulimail.product.vo.SpuSaveVo;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zqh.common.utils.PageUtils;
import com.zqh.common.utils.Query;

import com.zqh.gulimail.product.dao.SpuInfoDao;
import com.zqh.gulimail.product.entity.SpuInfoEntity;
import com.zqh.gulimail.product.service.SpuInfoService;
import org.springframework.transaction.annotation.Transactional;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {
        //保存spu基本信息pms_spu_info

        //保存spu的描述图片pms_spu_info_desc

        //保存spu的图片集pms_spu_images

        //保存spu的规格参数pms_product_attr_value

        //保存当前spu对应的所有sku信息
        //1）sku基本信息pms_sku_info
        //2）sku图片信息pms_sku_images
    }

}