package com.itliujiong.raggie.controller;

import com.itliujiong.raggie.common.R;
import com.itliujiong.raggie.entity.Employee;
import com.itliujiong.raggie.service.EmployeeService;
import com.itliujiong.raggie.service.impl.EmployeeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//TODO:这个是用mybatis做的登陆
//TODO:这个是用mybatis做的登陆
//TODO:这个是用mybatis做的登陆
//@Slf4j
@RestController
//@RestController 注解等同于同时使用 @Controller 和 @ResponseBody 注解,简化了控制层的开发。
//@RequestMapping("/employee")
public class EmployeeController {

// EmployeeService employeeService=new EmployeeServiceImpl();
// @Autowired 注解用于自动注入 EmployeeService 接口的实现类。这是依赖注入的体现,可以让控制器类不需要自己创建 EmployeeService 的实例,
// 而是由 Spring 容器自动注入。这样可以提高代码的可测试性和可维护性
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        //先对前端传来的密码进行md5加密
        String passwordOrigin=employee.getPassword();
        String passwordMD5= DigestUtils.md5DigestAsHex(passwordOrigin.getBytes());
        System.out.println(passwordOrigin+"   "+passwordMD5);
        employee.setPassword(passwordMD5);

        Employee employee1=employeeService.login(employee);
        System.out.println("@@@"+employee1);
//        由于字符串是对象类型，所以不能 简单的用“==” （双等号）判断两个字符串是否相等，
//        而使用 equals()方法比较两个对象的内 容。  返回：如果和 String 相等则为 true；否则为 false。
        System.out.println(passwordMD5==employee1.getPassword());

        //employee是前端传过来的数据
        if(employee1!=null && passwordMD5.equals(employee1.getPassword())){
            System.out.println("登陆成功！！！");

            //6。登陆成功,将员工id存入session并且返回登陆成功结果
            httpServletRequest.getSession().setAttribute("employee",employee1.getId());
            return R.success(employee1);
        } else if (employee1!=null && employee1.getStatus()==0) {
            return R.error("账号已禁用");

        } else {
            return R.error("登陆失败！！！");
        }
    }
}
