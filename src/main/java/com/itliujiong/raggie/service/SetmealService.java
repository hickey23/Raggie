package com.itliujiong.raggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itliujiong.raggie.common.R;
import com.itliujiong.raggie.entity.SetMealDto;
import com.itliujiong.raggie.entity.Setmeal;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    //新增套餐，同时保存套餐和菜品的关联关系
    public void addWithDish(SetMealDto setMealDto, HttpServletRequest request);

    //删除套餐，同时删除套餐和菜品的关联数据
    public void deleteWithDish(List<Long> ids);

    void updateStatusZero(Long ids);


    void updateStatusOne(Long ids);


    //移动端查询套餐
    public List<Setmeal> getSetmeal(Long categoryId, Integer status);
}
