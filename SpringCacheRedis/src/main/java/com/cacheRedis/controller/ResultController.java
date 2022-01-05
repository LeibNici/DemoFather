package com.cacheRedis.controller;

import com.cacheRedis.service.ResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultController {

    @Autowired
    private ResultServiceImpl resultService;

    @GetMapping("/get")
    public void test(){
        resultService.result();
    }

}
