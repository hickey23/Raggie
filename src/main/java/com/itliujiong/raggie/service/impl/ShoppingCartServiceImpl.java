package com.itliujiong.raggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itliujiong.raggie.common.R;
import com.itliujiong.raggie.entity.ShoppingCart;
import com.itliujiong.raggie.mapper.ShoppingCartMapper;
import com.itliujiong.raggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    //在实现类里面调用mapper
    @Override
    public Boolean cleanShoppingCart(Long userid) {
        System.out.println("ShoppingCartServiceImpl实现类里面的cleanShoppingCart...");
        Boolean is_delete=shoppingCartMapper.cleanShoppingCart(userid);
        return is_delete;

    }
}
