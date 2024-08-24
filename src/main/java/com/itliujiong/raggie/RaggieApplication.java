package com.itliujiong.raggie;
import com.itliujiong.raggie.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//
// 项目地址http://localhost:8088/backend/page/login/login.html
@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class RaggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(RaggieApplication.class,args);
        log.info("项目启动成功！！！");
        // Employee.say();
        // System.out.println(Employee.num);
    }
}
