package com.cacheRedis.service;

import com.cacheRedis.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResultServiceImpl {

    @Autowired
    private RedisService redisService;

    public Map<String, Object> result (){
        Map<String, Object> re = new HashMap<>();
        re.put("city","qingdao");
        re.put("country","zhongguo");
        redisService.setCacheObject("test",re);
        return re;
    }

}
