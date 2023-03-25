package com.itliujiong.raggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.itliujiong.raggie.common.R;
import com.itliujiong.raggie.entity.Category;
import com.itliujiong.raggie.mapper.CategoryMapper;
import com.itliujiong.raggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;

    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info(String.valueOf(category));

        QueryWrapper<Category> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",category.getName());
        List<Category> categoryList=categoryMapper.selectList(queryWrapper);
        System.out.println(categoryList);
        if (categoryList!=null && categoryList.isEmpty()){
            log.info("添加成功!!!");
            categoryService.save(category);
            return R.success("新增分类成功！！！");
        }
        return R.error("新增分类失败！！！");
    }

    @GetMapping("/page")
    public R<Page> getCategory(int page,int pageSize){
        log.info("category中的page={},pageSize={}",page,pageSize);
        //构造分页器
        Page<Category> pageInfo=new Page<>(page,pageSize);

        QueryWrapper<Category> queryWrapper=new QueryWrapper<>();

        //按表中的sort排序
        queryWrapper.orderByAsc("sort");
        categoryMapper.selectPage(pageInfo,queryWrapper);
        //查询的数据
        System.out.println("@@@@@ "+pageInfo.getRecords());
        return R.success(pageInfo);
    }

    @DeleteMapping
    public R<String> deletById(Long ids){

        System.out.println(ids);
        QueryWrapper<Category> queryWrapper=new QueryWrapper<>();

        queryWrapper.eq("id",ids);
        List<Category> categoryList=categoryMapper.selectList(queryWrapper);
        System.out.println("@@@@根据前端从后端查询到的数据是 "+categoryList);

        categoryService.remove(ids);

        return R.success("分类信息删除成功！！！");
    }


    @PutMapping
    public R<String> update(@RequestBody Category category, HttpServletRequest request){
        log.info("修改分类信息：{}",category);

        category.setUpdateTime(LocalDateTime.now());
        Long employeeId=(Long) request.getSession().getAttribute("employee");
//        System.out.println("id:::::"+employeeId);
        category.setUpdateUser(employeeId);

        //传入实体类
        categoryService.updateById(category);
        return R.success("修改分类信息成功！！！");
    }



    //根据条件查询分类数据
    //type=1是菜系。type=2是套餐
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        QueryWrapper<Category> queryWrapper=new QueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType()!=null,"type",category.getType());

        //添加排序条件
        queryWrapper.orderByAsc("sort").orderByDesc("update_time");

        List<Category> categoryList=categoryService.list(queryWrapper);
        log.info("@@@@ :"+categoryList);
        return R.success(categoryList);
    }



}
