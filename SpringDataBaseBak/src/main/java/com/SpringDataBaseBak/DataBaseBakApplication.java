package com.SpringDataBaseBak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author chenming
 * @description
 * @create: 2022-02-08
 */
@SpringBootApplication
@EnableScheduling
public class DataBaseBakApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataBaseBakApplication.class);
    }
}
