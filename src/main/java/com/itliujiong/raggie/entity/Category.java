package com.itliujiong.raggie.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {
    private static final long seriaVersionUID=1L;

    //解决java的long类型传到前端js精度只有16位，导致前后端id对应不上的问题
    @JsonSerialize(using= ToStringSerializer.class)
    private long id;

    //1。菜品分类，2。套餐分类
    private Integer type;
    private String name;

    private Integer sort;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
