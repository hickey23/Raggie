package com.itliujiong.raggie.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itliujiong.raggie.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


//定义实现具体的业务逻辑的方法
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
