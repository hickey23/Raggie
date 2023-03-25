package com.itliujiong.raggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itliujiong.raggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;

//继承mybatisplus
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
