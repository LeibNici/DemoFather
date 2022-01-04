package com.shardingSphere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShardingSphere {
    public static void main(String[] args) {
        SpringApplication.run(ShardingSphere.class);
    }
}
