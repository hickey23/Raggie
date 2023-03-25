package com.itliujiong.raggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itliujiong.raggie.entity.Orders;

import javax.servlet.http.HttpServletRequest;

public interface OrderService extends IService<Orders> {
    public void submit(Orders orders, HttpServletRequest request);
}
