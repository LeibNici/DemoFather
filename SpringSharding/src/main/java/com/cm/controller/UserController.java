package com.cm.controller;

import com.cm.domain.User;
import com.cm.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserMapper userMapper;

    @GetMapping("/test")
    public List<User> test(){
        User user = new User();
        user.setName("chenming");
        user.setCreateTime(new Date());
        userMapper.insert(user);

        return null;
    }

}
