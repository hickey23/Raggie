package com.itliujiong.raggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itliujiong.raggie.entity.User;
import com.itliujiong.raggie.mapper.UserMapper;
import com.itliujiong.raggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
