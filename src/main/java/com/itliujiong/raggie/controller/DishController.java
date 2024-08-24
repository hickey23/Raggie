package com.itliujiong.raggie.controller;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itliujiong.raggie.common.R;
import com.itliujiong.raggie.entity.*;
import com.itliujiong.raggie.mapper.DishMapper;
import com.itliujiong.raggie.service.CategoryService;
import com.itliujiong.raggie.service.DishFlaverService;
import com.itliujiong.raggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlaverService dishFlaverService;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;



    @PostMapping
// 那么上述参数可以改为以下形式：@requestBody User user 这种形式会将JSON字符串中的值赋予DishDto中对应的属性上
    public R<String> addDish(@RequestBody DishDto dishDto, DishFlavor dishFlavor, HttpServletRequest request){
//        log.info("获取到到dishDto是：{}",dishDto);

        dishService.addWithFlavor(dishDto,dishFlavor,request);
        System.out.println("---------------------------------------------");
        return R.success("新增菜品成功！！！");
    }


    @GetMapping("/page")
    public R<Page> getDish(int page, int pageSize,String name){
//        log.info("page={},pageSize={}",page,pageSize);
        Page<Dish> dishPageInfo=new Page<>(page,pageSize);

        Page<DishDto> dishDtoPageInfo=new Page<>(page,pageSize);

        QueryWrapper<Dish> queryWrapper=new QueryWrapper<>();
        //name不为空的时候
        queryWrapper.like(!StringUtils.isEmpty(name),"name",name);
        queryWrapper.orderByAsc("price");

        dishService.page(dishPageInfo,queryWrapper);
        log.info("查询到到所有数据是：{}",dishPageInfo.getRecords());

        //对象拷贝
        BeanUtils.copyProperties(dishPageInfo,dishDtoPageInfo,"records");
        List<Dish> allData=dishPageInfo.getRecords();
        List<DishDto> dishDtoList=new ArrayList<>();
        for(Dish dish:allData){
            DishDto dishDto=new DishDto();

            BeanUtils.copyProperties(dish,dishDto);

            Long categoryId=dish.getCategoryId();
            //根据id查询分类菜品
            Category category=categoryService.getById(categoryId);

            String categoryName=category.getName();
            dishDto.setCategoryName(categoryName);
            dishDtoList.add(dishDto);
        }
        log.info("allData中的数据是：{}",allData);
        log.info("dishDtoList:::::{}",dishDtoList);
        dishDtoPageInfo.setRecords(dishDtoList);

//{"id":"1413385247889891330","name":"米饭","categoryId":"1413384954989060097","price":200.00,"code":"","image":"ee04a05a-1230-46b6-8ad5-1a95b140fff3.png","description":"","status":1,"sort":0,"createTime":"2021-07-09 14:31:17","updateTime":"2021-07-11 16:35:26","createUser":"1","updateUser":"1","isDeleted":0,"flavors":[],"categoryName":"主食","copies":null}
        return R.success(dishDtoPageInfo);
    }


    //根据id查询菜品信息和对应的口味信息
    @GetMapping("/{id}")
    public R<DishDto> updateDish(@PathVariable Long id){
        log.info("update id:{}",id);
        DishDto dishDto=dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }



    //修改菜品
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto,HttpServletRequest request){
        dishService.updateWithFlavor(dishDto,request);
        System.out.println("-----------修改菜品和菜品口味信息成功！！！");
        return null;
    }



    //添加菜品中，根据条件查询对应的菜品数据
//    @GetMapping("/list")
//    public R<List<Dish>> list(@RequestParam Long categoryId){
//        System.out.println("categoryId::: "+categoryId);
//
//        //构造查询条件
//        QueryWrapper<Dish> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("category_id",categoryId);
//        queryWrapper.orderByAsc("sort").orderByDesc("update_time");
//
//        List<Dish> dishList=dishMapper.selectList(queryWrapper);
//        if (dishList.size()>0 && dishList!=null){
//            return R.success(dishList);
//        }
//
//        return R.error("查询不到对应的菜品信息！！！");
//    }



    @GetMapping("/list")
    public R<List<DishDto>> list(@RequestParam Long categoryId){
//        System.out.println("categoryId::: "+categoryId);

        //构造查询条件
        QueryWrapper<Dish> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("category_id",categoryId);
        queryWrapper.orderByAsc("sort").orderByDesc("update_time");

        List<Dish> dishList=dishMapper.selectList(queryWrapper);
        log.info("dishList::::{}",dishList);

        List<DishDto>dishDtoList=new ArrayList<>();
        for (Dish dish:dishList){
            DishDto dishDto=new DishDto();
            System.out.println("-------"+dish);
            BeanUtils.copyProperties(dish,dishDto);

            Long dish_id=dish.getId();
            Long category_id=dish.getCategoryId();
            Category category=categoryService.getById(category_id);

            QueryWrapper<DishFlavor> queryWrapper1=new QueryWrapper<>();
            queryWrapper1.eq("dish_id",dish_id);
            List<DishFlavor> dishFlavorList=dishFlaverService.list(queryWrapper1);
            System.out.println("@@@@:::"+dishFlavorList);
            String categoryName=category.getName();
            dishDto.setCategoryName(categoryName);
            dishDto.setFlavors(dishFlavorList);

            dishDtoList.add(dishDto);

        }

        return R.success(dishDtoList);
    }

}
