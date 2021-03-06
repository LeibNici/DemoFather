package com.ThreadPool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author chenming
 * @description
 * @create: 2022-02-11
 */
@SpringBootApplication
@EnableAsync
public class ThreadPoolApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThreadPoolApplication.class);
    }
}
