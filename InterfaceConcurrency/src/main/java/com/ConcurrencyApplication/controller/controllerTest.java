package com.ConcurrencyApplication.controller;

import com.ConcurrencyApplication.service.CurrentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Slf4j
public class controllerTest {

    @Autowired
    private CurrentService currentService;

    @GetMapping("/test1")
    public String test1() throws InterruptedException {
        log.info("test1正在执行---{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Thread.sleep(2000L);
        log.info("test1执行完毕---{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return "ok";
    }

    @GetMapping("/test2")
    @Async
    public String test2() throws InterruptedException {
        log.info("test2正在执行---{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Thread.sleep(3000L);
        log.info("test2执行完毕---{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return "ok";
    }

    @GetMapping("/test3")
    public String test3() throws InterruptedException {
        log.info("test3正在执行---{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        String s = currentService.test1();
        log.info("test3执行完毕---{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return "s";
    }

}
