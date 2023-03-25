package com.itliujiong.raggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itliujiong.raggie.entity.Dish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}
