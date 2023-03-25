package com.itliujiong.raggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itliujiong.raggie.common.CustomException;
import com.itliujiong.raggie.common.R;
import com.itliujiong.raggie.entity.SetMealDto;
import com.itliujiong.raggie.entity.Setmeal;
import com.itliujiong.raggie.entity.SetmealDish;
import com.itliujiong.raggie.mapper.SetmealMapper;
import com.itliujiong.raggie.service.SetmealDishService;
import com.itliujiong.raggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class SetmealImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService{

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealMapper setmealMapper;

    //新增套餐，同时保存套餐和菜品的关联关系
    @Override
    @Transactional
    public void addWithDish(SetMealDto setMealDto, HttpServletRequest request) {
        setMealDto.setCreateTime(LocalDateTime.now());
        setMealDto.setUpdateTime(LocalDateTime.now());
        setMealDto.setCreateUser((Long) request.getSession().getAttribute("employee"));
        setMealDto.setUpdateUser((Long) request.getSession().getAttribute("employee"));

        //保存套餐的基本信息,操作setmeal表，执行insert操作
        Boolean is_save=setmealService.save(setMealDto);
        if (is_save){
            List<SetmealDish> setmealDishList=setMealDto.getSetmealDishes();
            Long setmeal_id=setMealDto.getId();
//            ---------setmealDishList:[SetmealDish(id=null, setmealId=null, dishId=1637990028565098498, name=白切鸡套餐, price=6600, copies=1, sort=null, createTime=null, updateTime=null, createUser=null, updateUser=null, isDeleted=null),
//                    SetmealDish(id=null, setmealId=null, dishId=1413385247889891330, name=米饭, price=200, copies=1, sort=null, createTime=null, updateTime=null, createUser=null, updateUser=null, isDeleted=null),
//                    SetmealDish(id=null, setmealId=null, dishId=1413384757047271425, name=王老吉, price=500, copies=1, sort=null, createTime=null, updateTime=null, createUser=null, updateUser=null, isDeleted=null)]
            log.info("---------setmealDishList:{}",setmealDishList);

            //保存套餐和菜品的关联信息，操作setmeal_dish表，执行insert操作
            for (int i = 0; i < setmealDishList.size(); i++) {
                setmealDishList.get(i).setSetmealId(setmeal_id);
                setmealDishList.get(i).setCreateTime(LocalDateTime.now());
                setmealDishList.get(i).setUpdateTime(LocalDateTime.now());
                setmealDishList.get(i).setCreateUser((Long) request.getSession().getAttribute("employee"));
                setmealDishList.get(i).setUpdateUser((Long) request.getSession().getAttribute("employee"));
            }
            log.info("---------处理后的setmealDishList:{}",setmealDishList);
            //现在是修改套餐中的菜品信息，所以用setmealDishService接口
            Boolean is_success=setmealDishService.saveBatch(setmealDishList);
            if (is_success){
                log.info("新增套餐和套餐菜品信息成功！！！");
            }else {
                log.info("新增套餐和套餐菜品信息失败！！！");
            }

        }




    }
    //删除套餐，同时删除套餐和菜品的关联数据
    @Override
    public void deleteWithDish(List<Long> ids) {
        //查询套餐状态，确定是否能够删除
        QueryWrapper<Setmeal> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("id",ids);
        queryWrapper.eq("status",1);
        int count=this.count(queryWrapper);
        if (count>0){
            //查询出来数据，说明不能删除
            throw new CustomException("套餐正在售卖中，不能删除！！！");
        }
        //如果可以删除，先删除套餐表中的数据
        setmealService.removeByIds(ids);


        //删除关系表中的数据---setmeal_dish
        QueryWrapper<SetmealDish> queryWrapper1=new QueryWrapper<>();

        //delete from setmeal_dish where setmeal_id in ();
        queryWrapper1.in("setmeal_id",ids);

        //删除关系表中的数据
        setmealDishService.remove(queryWrapper1);



    }

    //修改status为0
    @Override
    public void updateStatusZero(Long ids) {
        System.out.println("实现类里面的updateStatusZero");
        setmealMapper.updateStatusZero(ids);
    }


    //修改status为1
    @Override
    public void updateStatusOne(Long ids) {
        System.out.println("实现类里面的updateStatusOne");
        setmealMapper.updateStatusOne(ids);
    }

    @Override
    public List<Setmeal> getSetmeal(Long categoryId, Integer status) {
        System.out.println(categoryId+" "+status);
        List<Setmeal> setmealList=setmealMapper.getSetmeal(categoryId,status);


        return setmealList;
    }


}
