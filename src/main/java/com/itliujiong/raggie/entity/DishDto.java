package com.itliujiong.raggie.entity;

import com.itliujiong.raggie.entity.Dish;
import com.itliujiong.raggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
