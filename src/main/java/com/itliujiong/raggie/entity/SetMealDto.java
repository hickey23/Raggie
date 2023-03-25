package com.itliujiong.raggie.entity;

import lombok.Data;

import java.util.List;

@Data
public class SetMealDto extends Setmeal{
    private List<SetmealDish> setmealDishes;
    private String categoryName;
}
