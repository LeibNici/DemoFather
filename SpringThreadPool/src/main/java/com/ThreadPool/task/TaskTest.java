package com.ThreadPool.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author chenming
 * @description
 * @create: 2022-02-11
 */
@Component
public class TaskTest {

    @Async("async")
    public void ss(int i) {
        System.out.println(Thread.currentThread().getName() + " " + i);
    }

}
