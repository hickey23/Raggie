package com.itliujiong.raggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itliujiong.raggie.entity.Dish;
import com.itliujiong.raggie.entity.DishDto;
import com.itliujiong.raggie.entity.DishFlavor;
import com.itliujiong.raggie.entity.Employee;
import com.itliujiong.raggie.mapper.DishFlavorMapper;
import com.itliujiong.raggie.mapper.DishMapper;
import com.itliujiong.raggie.service.DishFlaverService;
import com.itliujiong.raggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlaverService dishFlaverService;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;


    @Override
    public void addWithFlavor(DishDto dishDto, DishFlavor dishFlavor, HttpServletRequest request) {
        dishDto.setCreateTime(LocalDateTime.now());
        dishDto.setCreateUser((Long) request.getSession().getAttribute("employee"));
        dishDto.setUpdateTime(LocalDateTime.now());
        dishDto.setUpdateUser((Long) request.getSession().getAttribute("employee"));

        //保存菜品的基本信息到dish表
        dishService.save(dishDto);
//
        Long dishId=dishDto.getId();
        System.out.println("dishID："+dishId);

        List<DishFlavor> dishFlavors=dishDto.getFlavors();
        log.info("--------disFlavor:{}",dishFlavors);
        for(DishFlavor d:dishFlavors){
            System.out.println(d);
            d.setDishId(dishId);
            d.setCreateTime(LocalDateTime.now());
            d.setCreateUser((Long) request.getSession().getAttribute("employee"));
            d.setUpdateTime(LocalDateTime.now());
            d.setUpdateUser((Long) request.getSession().getAttribute("employee"));
            System.out.println("@@@@@@@@ ::::::"+d);
        }

        //保存菜品口味数据到菜品口味表：dish_flavor
        dishFlaverService.saveBatch(dishFlavors);
    }


    //根据id查询菜品信息和对应的口味信息
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息
        Dish dish=this.getById(id);
        log.info("菜品基本信息:{}",dish);
        DishDto dishDto=new DishDto();

        BeanUtils.copyProperties(dish,dishDto);

        //查询菜品对应的口味信息
        QueryWrapper<DishFlavor> queryWrapper=new QueryWrapper<>();
        //查询id是否和dishFlavor表中的dish_id项一致
        queryWrapper.eq("dish_id",id);


        List<DishFlavor> dishFlavorList=dishFlavorMapper.selectList(queryWrapper);
        if(dishFlavorList.size()==0){
            return null;
        }
        dishDto.setFlavors(dishFlavorList);
        return dishDto;
    }


    //修改菜品信息和菜品口味信息
    @Override
    public void updateWithFlavor(DishDto dishDto,HttpServletRequest request) {
        dishDto.setUpdateTime(LocalDateTime.now());

        //更新dish表菜品信息
        Boolean flag=dishService.updateById(dishDto);
        if (flag){
            Long dish_id=dishDto.getId();

            QueryWrapper<DishFlavor> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("dish_id",dish_id);
            //清理当前菜品对应的口味数据--dish_flavor表的delete操作
            dishFlaverService.remove(queryWrapper);

            List<DishFlavor> dishFlavorList=dishDto.getFlavors();
            for (DishFlavor d:dishFlavorList){
                System.out.println("####   "+d);
                if (d.getDishId()==null || d.getCreateTime()==null || d.getUpdateTime()==null || d.getCreateUser()==null || d.getUpdateUser()==null || d.getIsDeleted()==null){
                    d.setDishId(dish_id);
                    d.setCreateTime(LocalDateTime.now());
                    d.setUpdateTime(LocalDateTime.now());
                    d.setCreateUser((Long) request.getSession().getAttribute("employee"));
                    d.setUpdateUser((Long) request.getSession().getAttribute("employee"));
                    d.setIsDeleted(0);
                }
            }
            Boolean is_success=dishFlaverService.saveBatch(dishFlavorList);
            if (is_success){
                log.info("修改菜品信息和菜品口味信息成功！！！");
            }else {
                log.info("修改菜品信息和菜品口味信息失败！！！");
            }
        }
    }



}
