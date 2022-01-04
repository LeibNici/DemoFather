package com.shardingSphere.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shardingSphere.domain.User;
import com.shardingSphere.mapper.UserShardingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class DriverController {

    @Autowired
    private UserShardingMapper userShardingMapper;

    @GetMapping("/getDriver")
    public void test() {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.gt("create_time",new Date(1641018247000L));
        queryWrapper.lt("create_time",new Date(1646893447000L));
        List<User> users = userShardingMapper.selectList(queryWrapper);
        log.info(String.valueOf(users.size()));
    }

    @GetMapping("/insert")
    public void test2() {
        User user = new User();
        user.setName("ddd");
        user.setCreateTime(new Date(1643953796000L));
        user.setAge(16);
        userShardingMapper.insert(user);
    }

}
