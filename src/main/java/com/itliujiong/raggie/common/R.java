package com.itliujiong.raggie.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
//通用返回结果
@Data
public class R <T>{
    //1成功，0失败
    private Integer code;
    //错误信息，失败才有
    private String msg;
    private T data;
    private Map map=new HashMap();//动态数据


    //方法中形参类型不确定，在方法上申明定义自己的范性---------范性方法
    //修饰符 <类型> 返回值类型 方法名（类型 变量）
    //定义不确定的类型<T>
    public static <T> R<T> success(T object){
        R<T> r=new R<T>();
        r.data=object;
        r.code=1;
        return r;
    }

    public static <T> R<T> error(String msg){
        R<T> r=new R<T>();
        r.msg=msg;
        r.code=0;
        return r;
    }

    public R<T> add(String key,Object value){
        this.map.put(key,value);
        return this;
    }
}
