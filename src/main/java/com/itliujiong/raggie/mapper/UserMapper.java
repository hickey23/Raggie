package com.itliujiong.raggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itliujiong.raggie.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {
}
