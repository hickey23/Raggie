package com.itliujiong.raggie.service;

import com.itliujiong.raggie.entity.Employee;
import org.springframework.stereotype.Service;
// Service是一种用于处理业务逻辑的组件，它通常是业务逻辑的实际执行者
@Service
public interface EmployeeService {
    public Employee login(Employee employee);
}
