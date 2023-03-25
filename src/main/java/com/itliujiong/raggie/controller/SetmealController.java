package com.itliujiong.raggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itliujiong.raggie.common.R;
import com.itliujiong.raggie.entity.*;
import com.itliujiong.raggie.entity.SetMealDto;
import com.itliujiong.raggie.mapper.CategoryMapper;
import com.itliujiong.raggie.mapper.SetmealMapper;
import com.itliujiong.raggie.service.CategoryService;
import com.itliujiong.raggie.service.SetmealDishService;
import com.itliujiong.raggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealMapper setmealMapper;



    //新增套餐功能
    @PostMapping
    public R<String> addSetmeal(@RequestBody SetMealDto setMealDto, HttpServletRequest request){
        log.info("新增套餐为：：： {}",setMealDto);

        setmealService.addWithDish(setMealDto,request);
        return R.success("新增套餐成功！！！");
    }

    @GetMapping("/page")
    public R<Page> getSetmealPage(@RequestParam int page, int pageSize,String name){
        System.out.println(page+"  "+pageSize+"  "+name);
        //分页构造器
        Page<Setmeal> setmealPage=new Page<>(page,pageSize);
        Page<SetMealDto> setMealDtoPage=new Page<>(page,pageSize);


        QueryWrapper<Setmeal> queryWrapper=new QueryWrapper<>();
        //添加条件查询，根据name进行模糊查询
        queryWrapper.like(name!=null,"name",name);
        //根据更新时间降序排列
        queryWrapper.orderByDesc("price");
        setmealService.page(setmealPage,queryWrapper);

        //这里复制后setMealDtoPage除了records没有，其他的属性都和setmealPage一样
        BeanUtils.copyProperties(setmealPage,setMealDtoPage,"records");

        List<Setmeal> allData=setmealPage.getRecords();
        //新建集合
        List<SetMealDto> allDataForSetMealDto=new ArrayList<>();
        System.out.println("@@@allData::"+allData);
        for (Setmeal setmeal:allData){
            System.out.println("---------:"+setmeal);

            SetMealDto setMealDto=new SetMealDto();
            BeanUtils.copyProperties(setmeal,setMealDto);
            log.info("====setMealDto:::{}",setMealDto);
            log.info("======setMealDto.get:{}",setMealDto.getId(),setMealDto.getCategoryId());


            Long categoryId=setmeal.getCategoryId();
            Category category=categoryService.getById(categoryId);

            log.info("查出来的category是：{}",category);

            String categoryName=category.getName();
            setMealDto.setCategoryName(categoryName);
            allDataForSetMealDto.add(setMealDto);
        }
        System.out.println("allDataForSetMealDto:"+allDataForSetMealDto);
        setMealDtoPage.setRecords(allDataForSetMealDto);


        return R.success(setMealDtoPage);

    }

    //删除套餐和套餐对应的菜品信息
    @DeleteMapping
    public R<String> deleteSetmeal(@RequestParam List<Long> ids){
        System.out.println("ids:"+ids);
        setmealService.deleteWithDish(ids);
        return null;
    }


    //修改状态，把状态修改为0
    @PostMapping("/status/0")
    public R<String> stopSell(@RequestParam List<Long> ids){
        System.out.println("停售处理中-------------");
        for (Long id:ids){
            System.out.println(id);
            setmealService.updateStatusZero(id);
        }

        return R.success("修改状态成功！！！");

    }

    //修改状态，把状态修改为1
    @PostMapping("/status/1")
    public R<String> startSell(@RequestParam List<Long> ids){
        System.out.println("启售处理中----------------");
        for (Long id:ids){
            System.out.println(id);
            setmealService.updateStatusOne(id);
        }

        return R.success("修改状态成功！！！");
    }

    //根据条件查询套餐数据（移动端）
    @GetMapping("/list")
    public R<List<Setmeal>> getSetmealForFront(@RequestParam Long categoryId,Integer status){
        if (categoryId==null && status==null){
            R.error("查询套餐失败！！！");
        }

        System.out.println("数据都不为null ！！！");
        List<Setmeal> setmealList=setmealService.getSetmeal(categoryId,status);
        System.out.println("setmealList::::"+setmealList);
        if (setmealList.size()>0 && setmealList!=null){
            return R.success(setmealList);
        }

        return R.error("查询套餐失败！！！");

    }


}
