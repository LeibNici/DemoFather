package com.redisson.com.redisson;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @author chenming
 * @description
 * @create: 2022-03-01
 */

public class trylock {

    @Autowired
    private RedissonClient redissonClient;

    public void s() {
        RLock lock = redissonClient.getLock("123");
        try {
            boolean b = lock.tryLock(1, 2, TimeUnit.SECONDS);
            if (b) {
                System.out.println("111");
                lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
