package com.itliujiong.raggie.service.impl;

import com.itliujiong.raggie.entity.Employee;
import com.itliujiong.raggie.mapper.EmployeeMapper;
import com.itliujiong.raggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//controller调service，service调mapper
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public Employee login(Employee employee) {
        Employee employee1=employeeMapper.login(employee);
        return employee1;
    }
}
