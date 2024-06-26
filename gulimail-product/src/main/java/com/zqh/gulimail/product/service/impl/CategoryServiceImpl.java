package com.zqh.gulimail.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zqh.common.utils.PageUtils;
import com.zqh.common.utils.Query;

import com.zqh.gulimail.product.dao.CategoryDao;
import com.zqh.gulimail.product.entity.CategoryEntity;
import com.zqh.gulimail.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

/*  ServiceImpl里有继承baseMapper
    可以直接或者  baseMapper.selectList(条件);
    @Autowired
    CategoryDao categoryDao;*/
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);


        //组装陈父子的树形结构
        //1)找到所有的一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter((categoryEntity) -> {
            return categoryEntity.getParentCid() == 0;
        }).map((menu)->{
            menu.setChildren(getChildren(menu,entities));
            return menu;
        }).sorted((menu1,menu2)->{
            return (menu1.getSort()==null?0: menu1.getSort())-(menu2.getSort()==null?0: menu2.getSort());
        }).collect(Collectors.toList());

        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> list) {

        //tudo ,寻找待办事项（备忘录）
        //TODO 1.检查当前删除的菜单是否被别的地方引用
        baseMapper.deleteBatchIds(list);
    }

    //[2/25/225]
    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();

        List<Long> parentPath = findParentPath(catelogId, paths);

        Collections.reverse(parentPath);

        return paths.toArray(new Long[parentPath.size()]);
    }

    private List<Long> findParentPath(Long catelogId, List<Long> paths){

//        paths.add(catelogId);
//
//        CategoryEntity byId = this.getById(catelogId);
//        if(byId.getParentCid()!=0){
//            findParentPath(byId.getParentCid(),paths);
//        }

//        while(byId!=null&&byId.getParentCid()!=0){
//            paths.add(byId.getParentCid());
//        }
        while (catelogId!=null){
            paths.add(catelogId);
            CategoryEntity byId = this.getById(catelogId);
            if(byId!=null&&byId.getParentCid()!=0){
                catelogId = byId.getParentCid();
            }else{
                catelogId = null;
            }
        }

        return paths;
    }

    /*
    * 递归获取当前菜单的子菜单
    * root当前菜单，all所有菜单
    * */

    private List<CategoryEntity> getChildren(CategoryEntity root,List<CategoryEntity> all){

        List<CategoryEntity> children = all.stream().filter((categoryEntity) -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map((categoryEntity)->{
            //找到子菜单
            categoryEntity.setChildren(getChildren(categoryEntity,all));
            return categoryEntity;
        }).sorted((menu1,menu2)->{
            //菜单的排序
            return (menu1.getSort()==null?0: menu1.getSort())-(menu2.getSort()==null?0: menu2.getSort());
        }).collect(Collectors.toList());

        return children;
    }


}