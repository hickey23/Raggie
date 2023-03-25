package com.itliujiong.raggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itliujiong.raggie.common.CustomException;
import com.itliujiong.raggie.entity.Category;
import com.itliujiong.raggie.entity.Dish;
import com.itliujiong.raggie.entity.Setmeal;
import com.itliujiong.raggie.mapper.CategoryMapper;
import com.itliujiong.raggie.service.CategoryService;
import com.itliujiong.raggie.service.DishService;
import com.itliujiong.raggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//实现类交给spring管理
@Slf4j
@Service

//实现具体的业务逻辑
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;


    @Override
    public void remove(Long id){
        QueryWrapper<Dish> dishQueryWrapper=new QueryWrapper<>();
        //查询当前分类是否关联菜品
        //添加查询条件，根据分类id进行查询
        dishQueryWrapper.eq("category_id",id);
        //统计查询出来有多少个数据
        int count=dishService.count(dishQueryWrapper);
//        System.out.println("count:"+count);
        //查询当前分类是否关联了菜品，。如果已经关联了，抛出异常
        if (count>0){
            //已经关联了，抛出异常
            throw new CustomException("当前分类下关联了菜品，不能删除！！！");
        }

        //查询当前分类是否关联了套餐，如果关联抛出异常
        QueryWrapper<Setmeal> setmealQueryWrapper=new QueryWrapper<>();
        setmealQueryWrapper.eq("category_id",id);
        int count2=setmealService.count();
        if(count2>0){
            //已经关联了套餐
            throw new CustomException("当前分类关联了套餐，不能删除！！！");
        }

        //正常删除分类
        super.removeById(id);

    }


}
