package com.itliujiong.raggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itliujiong.raggie.common.CustomException;
import com.itliujiong.raggie.entity.*;
import com.itliujiong.raggie.mapper.OrderMapper;
import com.itliujiong.raggie.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper,Orders> implements OrderService {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    //点击去付款的回调函数
    @Override
    public void submit(Orders orders, HttpServletRequest request) {
        BigDecimal sum= new BigDecimal(0);
        //获取当前用户的id
        Long userid=(Long) request.getSession().getAttribute("user");
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("id",userid);
        User user=userService.getOne(userQueryWrapper);




        //查询当前用户的购物车数据
        QueryWrapper<ShoppingCart> shoppingCartQueryWrapper=new QueryWrapper<>();
        shoppingCartQueryWrapper.eq("user_id",userid);
        List<ShoppingCart> shoppingCartList=shoppingCartService.list(shoppingCartQueryWrapper);
        if (shoppingCartList==null||shoppingCartList.size()==0){
            throw new CustomException("购物车为空，不能下单！！！");
        }
        //查到当前用户的购物车
        //累加求总金额
        log.info("--------查到当前用户的购物车:::{}",shoppingCartList);
        //订单号suijishengcheng
        Long orderId= IdWorker.getId();
        List<OrderDetail> orderDetailList=new ArrayList<>();

        for (int i = 0; i < shoppingCartList.size(); i++) {
            BigDecimal amount=shoppingCartList.get(i).getAmount();
            sum=sum.add(amount);
            OrderDetail orderDetail=new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(shoppingCartList.get(i).getNumber());
            orderDetail.setDishFlavor(shoppingCartList.get(i).getDishFlavor());
            orderDetail.setDishId(shoppingCartList.get(i).getDishId());
            orderDetail.setSetmealId(shoppingCartList.get(i).getSetmealId());
            orderDetail.setName(shoppingCartList.get(i).getName());
            orderDetail.setImage(shoppingCartList.get(i).getImage());
            orderDetail.setAmount(sum);
            orderDetailList.add(orderDetail);

        }
        log.info("------sum:{} ",sum);
        log.info("@@@@@@@@ orderDetailList:{}",orderDetailList);
        //获取地址
        Long addressBookID=orders.getAddressBookId();
        AddressBook addressBook=addressBookService.getById(addressBookID);
        if (addressBook==null){
            throw new CustomException("用户地址信息有误，无法下单！！！");
        }



        //向订单表插入数据

        //String.valueOf将变量转化为字符串
        orders.setId(orderId);
        orders.setNumber(String.valueOf(orderId));
        orders.setStatus(2);
        orders.setUserId(userid);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress(
                (addressBook.getProvinceCode() == null ? "" : addressBook.getProvinceCode())
                        + (addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                        + (addressBook.getCityCode() == null ? "" : addressBook.getCityCode())
                        + (addressBook.getCityName() == null ? "" : addressBook.getCityName()));
        orders.setAmount(sum);
        log.info("order:{}",orders);
        orderService.save(orders);

        //向订单明细表插入数据，多条数据,saveBatch传入的是一个集合
        orderDetailService.saveBatch(orderDetailList);

        //清空购物车
        shoppingCartService.remove(shoppingCartQueryWrapper);


    }
}
