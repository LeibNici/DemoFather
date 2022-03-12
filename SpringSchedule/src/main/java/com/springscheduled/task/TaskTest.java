package com.springscheduled.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chenming
 * @description
 * @create: 2022-02-17
 */
@Component
public class TaskTest {

    @Scheduled(fixedRate = 2000)
    public void test1() throws InterruptedException {
        System.out.println("当前执行test1 " + Thread.currentThread().getName() + "  " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
    }

}
