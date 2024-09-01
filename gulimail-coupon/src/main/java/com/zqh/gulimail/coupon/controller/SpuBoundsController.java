package com.zqh.gulimail.coupon.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zqh.gulimail.coupon.entity.SpuBoundsEntity;
import com.zqh.gulimail.coupon.service.SpuBoundsService;
import com.zqh.common.utils.PageUtils;
import com.zqh.common.utils.R;



/**
 * 商品spu积分设置
 *
 * @author zqh
 * @email zqh@gmail.com
 * @date 2024-05-22 19:02:50
 */
@RestController
@RequestMapping("coupon/spubounds")
public class SpuBoundsController {
    @Autowired
    private SpuBoundsService spuBoundsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:spubounds:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spuBoundsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("coupon:spubounds:info")
    public R info(@PathVariable("id") Long id){
		SpuBoundsEntity spuBounds = spuBoundsService.getById(id);

        return R.ok().put("spuBounds", spuBounds);
    }

    /**
     * 保存
     *
     * 1. CouponFeignService.saveSpuBounds(spuBoundTo);
     * 1）、@RequestBody将这个对象转为json。
     * 2）、找到gulimaLL-coupon服务，给/coupon/sPubounds/save发送请求。
     * 将上一步转的json放在请求体位置，发送请求；
     *
     * 3）、对方服务收到请求。请求体里有json数据。
     * （@RequestBody, SpuBoundsEntity spuBounds）；将请求体的json转为SpuBoundsEntity；
     * 只要json数据模型是兼容的。双方服务无需使用同一个to
     */
    @PostMapping("/save")
    //@RequiresPermissions("coupon:spubounds:save")
    public R save(@RequestBody SpuBoundsEntity spuBounds){
		spuBoundsService.save(spuBounds);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:spubounds:update")
    public R update(@RequestBody SpuBoundsEntity spuBounds){
		spuBoundsService.updateById(spuBounds);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:spubounds:delete")
    public R delete(@RequestBody Long[] ids){
		spuBoundsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
