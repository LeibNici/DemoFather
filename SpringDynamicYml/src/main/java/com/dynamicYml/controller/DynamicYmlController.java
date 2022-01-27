package com.dynamicYml.controller;

import com.dynamicYml.domain.OrderProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenming
 * @description
 * @create: 2022-01-27
 */
@RestController
@RefreshScope
public class DynamicYmlController {

    @Autowired
    private OrderProperties orderProperties;

    @GetMapping("/test")
    public Object getYml(){
        return orderProperties.getName();
    }

}
