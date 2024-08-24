package com.itliujiong.raggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itliujiong.raggie.common.R;
import com.itliujiong.raggie.entity.Employee;
import com.itliujiong.raggie.mapper.EmployeeMapperByMp;
import com.itliujiong.raggie.service.EmployeeServiceByMp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

//TODO:这个是用mybatisPlus做的登陆
//TODO:这个是用mybatisPlus做的登陆
//TODO:这个是用mybatisPlus做的登陆

@Slf4j
@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/employee")
public class EmployeeControllerByMp {
    @Autowired
    private EmployeeServiceByMp employeeServiceByMp;
    @Autowired
    private EmployeeMapperByMp employeeMapperByMp;


    @PostMapping ("/login")
    //通过@requestBody可以将请求体中的JSON字符串绑定到相应的bean上，当然，也可以将其分别绑定到对应的字符串上。
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws InterruptedException {
        //1.将页面提交的密码进行md5加密处理
        String passwordOrigin=employee.getPassword();
        System.out.println(employee.getUsername()+"  "+employee.getPassword());
        String passwordMD5= DigestUtils.md5DigestAsHex(passwordOrigin.getBytes());
        System.out.println("passwordMD5:"+passwordMD5);



        //2.根据页面提交的用户名查询数据库
        LambdaQueryWrapper<Employee> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getUsername,employee.getUsername());
        //数据库中表的username加入了唯一性
        Employee employee1=employeeServiceByMp.getOne(lambdaQueryWrapper);
        if(employee1==null){
            return R.error("用户名不存在，登陆失败！！！");
        }else {
            //3.比对密码
            if(!employee1.getPassword().equals(passwordMD5)){
                return R.error("密码错误，登陆失败！！！");
            }
            //查看员工状态，如果禁用，返回员工已金庸结果
            if(employee1.getStatus()==0){
                return R.error("账号已禁用");
            }
            //6。登陆成功,将员工id存入session并且返回登陆成功结果
            httpServletRequest.getSession().setAttribute("employee",employee1.getId());
            System.out.println(employee1);
            //延迟3秒响应数据
            Thread thread = new Thread();
            thread.sleep(3000);
            return R.success(employee1);
        }
    }


    //员工退出接口
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest httpServletRequest){
        System.out.println("@@@@@@@@logout");
        //清理session中保存的当前员工的id
        httpServletRequest.getSession().removeAttribute("employee");
        return R.success("退出成功！！！");
    }

    //
    @PostMapping
    public R<String> add(@RequestBody Employee employee,HttpServletRequest httpServletRequest){
        log.info(String.valueOf(employee));

        //设置员工初始密码：123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //获取当前用户的id
        Long id=(Long) httpServletRequest.getSession().getAttribute("employee");
        employee.setCreateUser(id);
        employee.setUpdateUser(id);


        //查询是否有和前端数据一样的username
        QueryWrapper<Employee> queryWrapper=new QueryWrapper<Employee>();
        //查询条件
        queryWrapper.eq("username",employee.getUsername());
        List<Employee> employeeList=employeeMapperByMp.selectList(queryWrapper);
        System.out.println("employeeList:"+employeeList);
        //如果为空，说明查不出来
        if(employeeList!=null && employeeList.isEmpty()){
            System.out.println("添加成功！！！");
            employeeServiceByMp.save(employee);
            return R.success("新增员工成功！！！");
        }else {
            System.out.println("添加失败！！！");
            return R.error("添加员工失败！！！");
        }

    }

    //分页
    @GetMapping("/page")
    public R<Page> selectByPage(int page,int pageSize,String name,Employee employee){
        log.info("page={},pageSize={},name={}",page,pageSize,name);
        //构造分页构造器
        Page<Employee> pageInfo=new Page<>(page,pageSize);
        //构造条件构造器
        QueryWrapper<Employee> queryWrapper=new QueryWrapper<>();

        System.out.println(!StringUtils.isEmpty(name)+" "+name);
        //当查询到的name不等于空的时候添加tiaojian like
        queryWrapper.like(!StringUtils.isEmpty(name),"name",name);
        //添加排序条件
        queryWrapper.orderByDesc("update_time");
        //执行查询
        //这一句和employeeMapperByMp.selectPage（）一样都是执行查询的意思
//        employeeServiceByMp.page(pageInfo,queryWrapper);
        employeeMapperByMp.selectPage(pageInfo,queryWrapper);

        log.info("当前页码值={},每页显示条数={},一共多少页={}",pageInfo.getCurrent(),pageInfo.getSize(),pageInfo.getPages());
//        System.out.println("当前页码值："+pageInfo.getCurrent());
//        System.out.println("每页显示条数："+pageInfo.getSize());
//        System.out.println("一共多少页："+pageInfo.getPages());
        return R.success(pageInfo);
    }


    //根据id修改员工信息
    @PutMapping
    public R<String> update(@RequestBody Employee employee,HttpServletRequest httpServletRequest){
        //string.valueof，如果为null会返回"null"
        log.info(String.valueOf(employee));
        //打印session：org.apache.catalina.session.StandardSessionFacade@350f220
        //getAttribute获取id
//        System.out.println(httpServletRequest.getSession().getAttribute("employee"));

        Long employeeId=(Long) httpServletRequest.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(employeeId);

        log.info(String.valueOf(employee));

        employeeServiceByMp.updateById(employee);
        return R.success("员工信息修改成功！！！");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
//        System.out.println("id:"+id);
        Employee employee=employeeServiceByMp.getById(id);
        if(employee!=null){
            return R.success(employee);
        }
        return R.error("没有查询到对应的员工信息！！！");
    }



}
