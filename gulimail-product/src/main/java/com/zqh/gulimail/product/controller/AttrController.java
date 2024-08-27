package com.zqh.gulimail.product.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.zqh.gulimail.product.vo.AttrRespVo;
import com.zqh.gulimail.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zqh.gulimail.product.entity.AttrEntity;
import com.zqh.gulimail.product.service.AttrService;
import com.zqh.common.utils.PageUtils;
import com.zqh.common.utils.R;



/**
 * 商品属性
 *
 * @author zqh
 * @email zqh@gmail.com
 * @date 2024-05-22 00:39:17
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;




    // /product/attr/base/list/{catelogId}
    @GetMapping("/{attrType}/list/{catelogId}")
    public R baseAttrList(@RequestParam() Map<String, Object> params,
                          @PathVariable("catelogId") Long catelogId,
                          @PathVariable("attrType") String type) {
        PageUtils page = attrService.queryBaseAttrPage(params, catelogId, type);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
		//AttrEntity attr = attrService.getById(attrId);
        AttrRespVo respVo = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", respVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attr){
		attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attr){
		attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
