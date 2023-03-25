package com.itliujiong.raggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itliujiong.raggie.common.R;
import com.itliujiong.raggie.entity.Orders;
import com.itliujiong.raggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders, HttpServletRequest request){
        log.info("接受到的order数据是:{}",orders);
        orderService.submit(orders,request);
        return R.success("付款成功！！！");
    }


    //查看订单页码
    @GetMapping("/userPage")
    public R<Page> checkOrderList(@RequestParam int page , int pageSize){
        System.out.println(page+" "+pageSize);
        Page<Orders> ordersPage=new Page<>();
        QueryWrapper<Orders> ordersQueryWrapper=new QueryWrapper<>();
//        ordersQueryWrapper.orderByDesc("create_time");

        orderService.page(ordersPage,ordersQueryWrapper);

        log.info("获取到的订单信息是：{}",ordersPage);
        return R.success(ordersPage);
    }
}
