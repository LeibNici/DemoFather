package com.cacheRedis.service;

import com.cacheRedis.annotation.RedisCacheAble;
import com.cacheRedis.domain.Driver;
import com.cacheRedis.domain.vo.DriverVo;
import com.cacheRedis.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ResultServiceImpl {

    @Autowired
    private RedisService redisService;

    @RedisCacheAble(key = "123",transfer = DriverVo.class,expireTime = 100)
    public Object result() {
        Driver driver = new Driver();
        driver.setName("chenming");
        driver.setCity("qingdao");
        driver.setCountry("zhongguo");
        return driver;
    }

}
