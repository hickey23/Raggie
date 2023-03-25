package com.itliujiong.raggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itliujiong.raggie.common.R;
import com.itliujiong.raggie.entity.Dish;
import com.itliujiong.raggie.entity.ShoppingCart;
import com.itliujiong.raggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;


    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart, HttpServletRequest request){
        System.out.println("shoppingCart:"+shoppingCart);
        //设置用户id，指定当前是哪一个用户的购物车
        Long currentId=(Long) request.getSession().getAttribute("user");
        //
        shoppingCart.setUserId(currentId);

        //查询当前菜品或者套餐是否在购物车中
        Long dishId=shoppingCart.getDishId();
        Long setmealId=shoppingCart.getSetmealId();
        QueryWrapper<ShoppingCart> shoppingCartQueryWrapper=new QueryWrapper<>();
        if (dishId!=null){
            //添加到购物车中的是菜品
            shoppingCartQueryWrapper.eq("user_id",currentId);
            shoppingCartQueryWrapper.eq("dish_id",dishId);


        }else {
            shoppingCartQueryWrapper.eq("user_id",currentId);
            shoppingCartQueryWrapper.eq("setmeal_id",setmealId);

        }
        //sql:select * from shopping_cart where user_id=? and dish_id=? /setmeal_id=?
        ShoppingCart shoppingCart_one=shoppingCartService.getOne(shoppingCartQueryWrapper);
        log.info("shoppingCart_one:::{}",shoppingCart_one);

        //如果已经存在就在原来的基础上加一
        if (shoppingCart_one!=null){
            Integer num=shoppingCart_one.getNumber();
            shoppingCart_one.setNumber(num+1);
            shoppingCart_one.setCreateTime(LocalDateTime.now());
            //更新操作
            shoppingCartService.updateById(shoppingCart_one);
        }else {
            //如果不存在，则添加到购物车，数量默认就是一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCart.setUserId((Long) request.getSession().getAttribute("user"));
            //insert操作
            shoppingCartService.save(shoppingCart);
            shoppingCart_one=shoppingCart;
        }
        return R.success(shoppingCart_one);
    }


    @GetMapping("/list")
    public R<List<ShoppingCart>> getShoppingCartList(HttpServletRequest request){
        log.info("查询购物车中的数据...");

        QueryWrapper<ShoppingCart> queryWrapper=new QueryWrapper<>();
        Long user_id=(Long) request.getSession().getAttribute("user");
        queryWrapper.eq("user_id",user_id);
        queryWrapper.orderByDesc("create_time");

        List<ShoppingCart> shoppingCartList=shoppingCartService.list(queryWrapper);
        log.info("查询购物车中的数据...{}",shoppingCartList);
        if (shoppingCartList!=null){
            return R.success(shoppingCartList);
        }


        return R.error("查询购物车失败！！！");
    }


    @DeleteMapping("/clean")
    public R<String> cleanShoppingCart(HttpServletRequest request){
        Long userid=(Long) request.getSession().getAttribute("user");
        System.out.println(userid);
        Boolean is_delete=shoppingCartService.cleanShoppingCart(userid);
        if (!is_delete) {
            return R.error("清空购物车失败！！！");
        }
        return R.success("清空购物车成功！！！");

    }


}
