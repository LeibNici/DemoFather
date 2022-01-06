package com.cacheRedis.service;

import com.cacheRedis.annotation.RedisCacheAble;
import com.cacheRedis.domain.Driver;
import com.cacheRedis.domain.vo.DriverVo;
import com.cacheRedis.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ResultServiceImpl {

    @Autowired
    private RedisService redisService;

    @RedisCacheAble(key = "123", transfer = DriverVo.class, expireTime = 100)
    public Object result() {
        Map<Integer,Driver> list = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            Driver driver = new Driver();
            driver.setName("chenming" + i);
            driver.setCity("qingdao" + i);
            driver.setCountry("zhongguo" + i);
            list.put(i,driver);
        }
        return list;
    }

}
