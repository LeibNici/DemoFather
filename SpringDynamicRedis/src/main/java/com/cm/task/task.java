package com.cm.task;

import com.cm.redis.role.RedisServiceOrder;
import com.cm.redis.role.RedisServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class task {

    @Autowired
    private RedisServiceOrder redisServiceOrder;

    @Autowired
    private RedisServiceUser redisServiceUser;

    @Scheduled(fixedRate = 1000)
    public void compare(){

    }

}
