package com.springscheduled.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author chenming
 * @description
 * @create: 2022-02-17
 */
@Component
public class TaskTest {

    @Scheduled(fixedRate = 500)
    public void test1() throws InterruptedException {
        System.out.println("当前执行test1 " + Thread.currentThread().getName() + "  " + System.currentTimeMillis());
        Thread.sleep(2000);
    }

}
