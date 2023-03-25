package com.itliujiong.raggie.common;

//TODO:全局异常处理

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

//不管哪一个类上，只要加了这两个注解就会被这个类处理
@Slf4j
@ResponseBody
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public R<String> exceptionHandler(SQLException sqlException){
        log.error(sqlException.getMessage());
        if(sqlException.getMessage().contains("Duplicate entry")){
            System.out.println(sqlException.getMessage());
            String[] splits=sqlException.getMessage().split(" ");
            String msg=splits[2]+"已经存在";
            return R.error(msg);
        }
        return R.error("异常!!!");
    }


    //捕获自定义异常
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException customException){
        log.info(customException.getMessage());
        return R.error("Error:"+customException.getMessage());
    }

}
