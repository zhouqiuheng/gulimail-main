package com.zqh.gulimail.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.zqh.gulimail.product.entity.AttrEntity;
import com.zqh.gulimail.product.service.AttrAttrgroupRelationService;
import com.zqh.gulimail.product.service.AttrService;
import com.zqh.gulimail.product.service.CategoryService;
import com.zqh.gulimail.product.vo.AttrGroupRelationVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zqh.gulimail.product.entity.AttrGroupEntity;
import com.zqh.gulimail.product.service.AttrGroupService;
import com.zqh.common.utils.PageUtils;
import com.zqh.common.utils.R;



/**
 * 属性分组
 *
 * @author zqh
 * @email zqh@gmail.com
 * @date 2024-05-22 00:39:17
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

//   AttrEntity类通常是一个持久化实体类，它被映射到数据库中的一张表。实体类通常不会被Spring管理为Bean，所以不能直接在Spring容器中进行注入。
    @Autowired
    AttrService attrService;

    @Autowired
    AttrAttrgroupRelationService relationService;



//    @Autowired
//    private AttrAttrgroupRelationService relationService;


    ///product/attrgroup/attr/relation
    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> vos){
        relationService.saveBatch(vos);
        return R.ok();
    }



    ///product/attrgroup/{attrgroupId}/noattr/relation
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoRelation(@PathVariable("attrgroupId") Long attrgroupId,
                            @RequestParam Map<String,Object> params){
        PageUtils page = attrService.getNoRelationAttr(params,attrgroupId);
        return R.ok().put("page",page);
    }
    //delete from pms_attr_attrgroup_relation where (attr_id=1 and attr_group_id = 1)
    // or(attr_id=2 and attr_group_id = 3)
    //or.....
    //一直or下去，所以是个集合
    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos){
        attrService.deleteRelation(vos);
        return R.ok();
    }

    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {
        List<AttrEntity> entities = attrService.getRelationAttr(attrgroupId);
        return R.ok().put("data", entities);
    }
    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("catelogId") Long catelogId){

        //PageUtils page = attrGroupService.queryPage(params);

        PageUtils page = attrGroupService.queryPage(params,catelogId);


        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        Long catelogId = attrGroup.getCatelogId();

        Long[] path = categoryService.findCatelogPath(catelogId);

        attrGroup.setCatelogPath(path);

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
