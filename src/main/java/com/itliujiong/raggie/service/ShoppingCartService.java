package com.itliujiong.raggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itliujiong.raggie.common.R;
import com.itliujiong.raggie.entity.ShoppingCart;

public interface ShoppingCartService extends IService<ShoppingCart> {

    public Boolean cleanShoppingCart(Long userid);
}
