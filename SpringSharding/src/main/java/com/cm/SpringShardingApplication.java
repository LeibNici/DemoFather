package com.cm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cm.com.SpringPoints.mapper")
public class SpringShardingApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringShardingApplication.class);
    }
}
