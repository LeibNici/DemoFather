package com.ConcurrencyApplication.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CurrentService {

    public String test1() throws InterruptedException {
        Thread.sleep(4000);
        return "ok";
    }

}
