package com.SpringMQ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author chenming
 * @description
 * @create: 2022-03-03
 */
@SpringBootApplication
@EnableScheduling
public class MQPApplication {
    public static void main(String[] args) {
        SpringApplication.run(MQPApplication.class);
    }
}
