package com.zqh.gulimail.product.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.zqh.gulimail.product.dao.AttrAttrgroupRelationDao;
import com.zqh.gulimail.product.dao.AttrGroupDao;
import com.zqh.gulimail.product.dao.CategoryDao;
import com.zqh.gulimail.product.entity.AttrAttrgroupRelationEntity;
import com.zqh.gulimail.product.entity.AttrGroupEntity;
import com.zqh.gulimail.product.entity.CategoryEntity;
import com.zqh.gulimail.product.vo.AttrRespVo;
import com.zqh.gulimail.product.vo.AttrVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zqh.common.utils.PageUtils;
import com.zqh.common.utils.Query;

import com.zqh.gulimail.product.dao.AttrDao;
import com.zqh.gulimail.product.entity.AttrEntity;
import com.zqh.gulimail.product.service.AttrService;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationDao relationDao;


    @Autowired
    AttrGroupDao attrGroupDao;


    @Autowired
    CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveAttr(AttrVo attr) {
        //先传给数据库已经有的信息
        AttrEntity attrEntity = new AttrEntity();
        //如果一个个set，get太多了
        //attrEntity.setAttrName(attr.getAttrName());
        //导org那个包，这个方法叫复制参数，里面的参数分别是从哪到哪
        //注意两个参数名要一一对应
        BeanUtils.copyProperties(attr,attrEntity);
        this.save(attrEntity);

        //保存关联关系
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrGroupId(attr.getAttrGroupId());
        relationEntity.setAttrId(attrEntity.getAttrId());
        relationDao.insert(relationEntity);
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();

        if(catelogId != 0){
          queryWrapper.eq("catelog_id",catelogId);
        }

        String key = (String) params.get("key");

        if(!StringUtils.isEmpty(key)){
            queryWrapper.and((wrapper)->{
                wrapper.eq("attr_id",key).or().like("attr_name",key);
            });
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );

        PageUtils pageUtils = new PageUtils(page);
        //查记录
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> respVos = records.stream().map((attrEntity) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);

            //设置分类和分组的名字
            //获得属性ID
            AttrAttrgroupRelationEntity attrId = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
                    .eq("attr_id", attrEntity.getAttrId()));
            //可获得组ID

            if (attrId != null) {
                Long attrGroupId = attrId.getAttrGroupId();
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
                attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }

            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }


            return attrRespVo;
        }).collect(Collectors.toList());
        pageUtils.setList(respVos);
        return pageUtils;
    }

}