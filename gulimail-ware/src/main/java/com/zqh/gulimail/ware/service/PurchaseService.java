package com.zqh.gulimail.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zqh.common.utils.PageUtils;
import com.zqh.common.vo.ware.MergeVO;
import com.zqh.common.vo.ware.PurchaseDoneVO;
import com.zqh.gulimail.ware.entity.PurchaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author zqh
 * @email zqh@gmail.com
 * @date 2024-05-22 20:29:37
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceive(Map<String, Object> params);

    void mergePurchase(MergeVO mergeVo);

    void received(List<Long> ids);


    void done(PurchaseDoneVO doneVo);
}

