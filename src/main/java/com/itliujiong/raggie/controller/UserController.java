package com.itliujiong.raggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itliujiong.raggie.common.R;
import com.itliujiong.raggie.common.SMSUtils;
import com.itliujiong.raggie.entity.User;
import com.itliujiong.raggie.mapper.UserMapper;
import com.itliujiong.raggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private UserMapper userMapper;

    @PostMapping("/sendMsg")
    public R<StringBuilder> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone=user.getPhone();
        System.out.println(phone);
        StringBuilder code=new StringBuilder();
        if (!StringUtils.isEmpty(phone)){
            //生成随机的4位验证码
            double RandomCode=Math.random();
            if (RandomCode<=0.1){
                RandomCode=Math.random();
            }
            RandomCode=RandomCode*10000;
            String str_num=String.format("%.4f",RandomCode);
            System.out.println("str_num:::"+str_num);

            for (int i = 0; i < str_num.length(); i++) {
                char item = str_num.charAt(i);
                if (i<4){
                    System.out.println("###");
                    code.append(String.valueOf(item));
                }else {
                    break;
                }
            }
            log.info("code={}",code);
            //调用阿里云的短信api，发送短信
//            SMSUtils.sendMessage("瑞吉外卖","SMS_274590866",phone, String.valueOf(code));
            //将生成的验证码保存在session中
//            session.setAttribute(phone,code);

            //将生成的验证码保存在redis中，并且设置时间5分钟
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);

            return R.success(code);
        }
        //将生成的验证码保存起来，到session中，用于登陆的对比
        return R.error("手机验证码短信发送失败！！！");
    }


    @PostMapping("/login")
    public R<User> login(@RequestBody Map map,HttpSession session){
        System.out.println(map.toString());

        //获取手机号
        String phone=map.get("phone").toString();

        //获取验证码
        String code=map.get("code").toString();

        //从redis中获取缓存的验证码
        Object codeInRedis=redisTemplate.opsForValue().get(phone);

        //进行验证码比对
        Object sessionCode=session.getAttribute(phone);
        if (codeInRedis!=null && codeInRedis.toString().equals(code)){
            QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("phone",phone);

            User user=userService.getOne(queryWrapper);
            if (user==null){
                User user1=new User();
                user1.setPhone(phone);
                user1.setStatus(1);
                userService.save(user1);
            }
            session.setAttribute("user",user.getId());

        //如果用户登陆成功,删除redis中的缓存吗
        redisTemplate.delete(phone);
        return R.success(user);
    }


        return R.error("登陆失败！！！");
    }
}
