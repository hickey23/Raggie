package com.itliujiong.raggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itliujiong.raggie.common.R;
import com.itliujiong.raggie.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
    @Delete("delete from raggie.shopping_cart where user_id=#{userid}")
    public Boolean cleanShoppingCart(Long userid);
}
