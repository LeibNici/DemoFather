package com.cm.sentinel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenming
 * @description
 * @create: 2022-04-07
 */
@RestController
public class TestSentinelController {

    @GetMapping("/test")
    public String test() throws InterruptedException {
        Thread.sleep(3000);
        return "123";
    }

}
