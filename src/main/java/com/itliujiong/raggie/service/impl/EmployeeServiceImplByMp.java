package com.itliujiong.raggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itliujiong.raggie.entity.Employee;
import com.itliujiong.raggie.mapper.EmployeeMapperByMp;
import com.itliujiong.raggie.service.EmployeeServiceByMp;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImplByMp extends ServiceImpl<EmployeeMapperByMp, Employee>  implements EmployeeServiceByMp {
}
