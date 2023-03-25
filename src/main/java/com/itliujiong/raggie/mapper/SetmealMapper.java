package com.itliujiong.raggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itliujiong.raggie.common.R;
import com.itliujiong.raggie.entity.Setmeal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {

    @Update("update raggie.setmeal set status=0 where id=#{id}")
    void updateStatusZero(Long ids);

    @Update("update raggie.setmeal set status=1 where id=#{id}")
    void updateStatusOne(Long ids);

    @Select("select * from raggie.setmeal where category_id=#{categoryId} and status=#{status}")
    public List<Setmeal> getSetmeal(Long categoryId, Integer status);
}
