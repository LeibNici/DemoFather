package com.cacheRedis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringCacheRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCacheRedisApplication.class);
    }

}
