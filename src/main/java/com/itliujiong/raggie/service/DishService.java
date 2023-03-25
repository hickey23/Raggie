package com.itliujiong.raggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itliujiong.raggie.entity.Dish;
import com.itliujiong.raggie.entity.DishDto;
import com.itliujiong.raggie.entity.DishFlavor;
import com.itliujiong.raggie.entity.Employee;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish，dish_flavor
    @Transactional
    public void addWithFlavor(DishDto dishDto, DishFlavor dishFlavor, HttpServletRequest request);


    //根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);

    //更新菜品信息
    public void updateWithFlavor(DishDto dishDto,HttpServletRequest request);
}
