package com.shardingSphere.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shardingSphere.domain.Driver;
import com.shardingSphere.domain.User;
import com.shardingSphere.mapper.DriverMapper;
import com.shardingSphere.mapper.UserShardingMapper;
import com.shardingSphere.utils.TableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class DriverController {

    @Autowired
    private UserShardingMapper userShardingMapper;
    @Autowired
    private DriverMapper driverMapper;

    @GetMapping("/getUser")
    public void test() {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.gt("create_time", new Date(1641018247000L));
        queryWrapper.lt("create_time", new Date(1646893447000L));
        List<User> users = userShardingMapper.selectList(queryWrapper);
        log.info(String.valueOf(users.size()));
    }

    @GetMapping("/insert")
    public void test2() {
        User user = new User();
        user.setCreateTime(new Date(1649060097000L));
        user.setName("chenming");
        user.setAge(18);
        userShardingMapper.myInsert(user);
    }

    @GetMapping("/insertDriver")
    public void test3() {
        Driver driver = new Driver();
        driver.setName("chenming");
        driver.setDept("beidou");
        driver.setCreate_time(new Date());
        driverMapper.insert(driver);
    }

    @GetMapping("/getDriver")
    public void test4() {
        QueryWrapper<Driver> driverQueryWrapper = new QueryWrapper<>();
        driverQueryWrapper.eq("create_time", new Date(1641284680000L));
        Driver driver = driverMapper.selectOne(driverQueryWrapper);
        log.info(driver.getDept());
    }

    @GetMapping("/showCreateTable")
    public void test5() {
        Map<Object,Object> s = TableUtil.showCreateTable();
        s.size();
    }

}
