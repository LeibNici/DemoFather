package com.cacheRedis.controller;

import com.alibaba.fastjson.JSON;
import com.cacheRedis.service.ResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultController {

    @Autowired
    private ResultServiceImpl resultService;

    @GetMapping("/get")
    public String test() {
        return JSON.toJSONString(resultService.result());
    }

}
