package com.itliujiong.raggie.mapper;

import com.itliujiong.raggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Component （把普通pojo实例化到spring容器中，相当于配置文件中的 <bean>）
@Component

/**
 * 添加了@Mapper注解之后这个接口在编译时会生成相应的实现类
 *
 * 需要注意的是：这个接口中不可以定义同名的方法，因为会生成相同的id
 * 也就是说这个接口是不支持重载的
 */
@Mapper
public interface EmployeeMapper {
    //登陆
    @Select("select * from raggie.employee where username=#{username} and password=#{password}")
    public Employee login(Employee employee);
}
