package com.zqh.gulimail.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zqh.common.constant.product.AttrConstant;
import com.zqh.gulimail.product.dao.AttrAttrgroupRelationDao;
import com.zqh.gulimail.product.dao.AttrGroupDao;
import com.zqh.gulimail.product.dao.CategoryDao;
import com.zqh.gulimail.product.entity.AttrAttrgroupRelationEntity;
import com.zqh.gulimail.product.entity.AttrGroupEntity;
import com.zqh.gulimail.product.entity.CategoryEntity;
import com.zqh.gulimail.product.service.CategoryService;
import com.zqh.gulimail.product.vo.AttrGroupRelationVo;
import com.zqh.gulimail.product.vo.AttrRespVo;
import com.zqh.gulimail.product.vo.AttrVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
import org.springframework.transaction.annotation.Transactional;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationDao relationDao;


    @Autowired
    AttrGroupDao attrGroupDao;


    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AttrDao attrDao;

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
        if (attr.getAttrType() == AttrConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attr.getAttrGroupId()!=null){
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(relationEntity);
        }

    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type) {
        //equalsIgnoreCase() 方法用于将字符串与指定的对象比较，不考虑大小写。
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("attr_type","base"
                        .equalsIgnoreCase(type)?AttrConstant.AttrEnum.ATTR_TYPE_BASE.getCode():AttrConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

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

            //销售属性没有分组，传分组的attr_group_id会导致空指针异常
            if ("base".equalsIgnoreCase(type)){
                AttrAttrgroupRelationEntity attrId = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
                        .eq("attr_id", attrEntity.getAttrId()));
                if (attrId != null && attrId.getAttrGroupId()!=null) {
                    Long attrGroupId = attrId.getAttrGroupId();
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
            //可获得组ID

            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }


            return attrRespVo;
        }).collect(Collectors.toList());
        pageUtils.setList(respVos);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrRespVo respVo = new AttrRespVo();
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity,respVo);

        if (attrEntity.getAttrType() == AttrConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            //设置分组信息
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = relationDao
                    .selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
                            .eq("attr_id", attrId));
            if(attrAttrgroupRelationEntity != null){
                respVo.setAttrGroupId(attrAttrgroupRelationEntity.getAttrGroupId());

                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
                if(attrGroupEntity != null){
                    respVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }

            }
        }



        //设置分类信息
        Long catelogId = attrEntity.getCatelogId();
        Long[] catelogPath = categoryService.findCatelogPath(catelogId);
        respVo.setCatelogPath(catelogPath);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if (categoryEntity != null){
            respVo.setCatelogName(categoryEntity.getName());
        }

        return respVo;
    }

    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.updateById(attrEntity);

        if (attrEntity.getAttrType() == AttrConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            //这是在找一个sql语句里where的地方，为了确定他是新增还是修改，用一个count计数
            //如果count为0是新增其他是修改
            Integer count = relationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>()
                    .eq("attr_id", attr.getAttrId()));
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();

            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attr.getAttrId());
            if (count>0){
                //修改分组关联
                relationDao.update(relationEntity,new UpdateWrapper<AttrAttrgroupRelationEntity>()
                        .eq("attr_id",attr.getAttrId()));
            }else {
                relationDao.insert(relationEntity);
            }
        }


    }


    //根据分组id查找关联的所有属性
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {

        List<AttrAttrgroupRelationEntity> entities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>()
                .eq("attr_group_id", attrgroupId));

        List<Long> attrIds = entities.stream().map((attr) -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());

        if (attrIds == null || attrIds.size() == 0){
            return null;
        }

        List<AttrEntity> attrEntities = this.listByIds(attrIds);
        return  attrEntities;
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        //relationDao.delete(new QueryWrapper<>().eq("attr_id",1L).eq("attr_group_id",1L));
        //不要单独一条一条的删除语句

        //relationDao传的AttrAttrgroupRelationEntity
        //将数组vos里面的数据封装成一个AttrAttrgroupRelationEntity类的数据
        List<AttrAttrgroupRelationEntity> entites = Arrays.asList(vos).stream().map((item) -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());

        relationDao.deleteBatchRelation(entites);
    }


    //获取当前分组没有关联的所有属性
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
        //当前分组只能关联自己所属分类里面的所有属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();
        //当前分组只能关联其他分组没有引用的属性
        //1)当前分类下的其它分组
        List<AttrGroupEntity> group= attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>()
                .eq("catelog_id", catelogId));
        //收集所有当前分类下所有的分组id
        List<Long> collect = group.stream().map((item) -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());
        //2）这些分组关联的属性
        //in()符合多个条件的值
        List<AttrAttrgroupRelationEntity> groupId = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>()
                .in("attr_group_id", collect));
        List<Long> attrIds = groupId.stream().map((item) -> {
            return item.getAttrId();
        }).collect(Collectors.toList());
        //3）从当前分类的所有属性中移出这些属性
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>()
                .eq("catelog_id", catelogId)
                .eq("attr_type", AttrConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if (attrIds!=null&&attrIds.size()>0){
            wrapper.notIn("attr_id", attrIds);
        }
        String key = (String) params.get("key");
//      查询功能
        if (!StringUtils.isEmpty(key)){
           wrapper.and((w)->{
              w.eq("attr_id",key).or().like("attr_name",key);
           });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );
        PageUtils pageUtils = new PageUtils(page);

        return pageUtils;
    }

}