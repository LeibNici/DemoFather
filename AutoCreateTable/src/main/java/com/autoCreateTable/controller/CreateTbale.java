package com.autoCreateTable.controller;

import com.autoCreateTable.mapper.TableCheckMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CreateTbale {

    @Autowired
    private TableCheckMapper tableCheckMapper;

    @GetMapping("/create")
    public String table(){
        Map<Object, Object> map = tableCheckMapper.showCreateSql("show create table bus_car_operation_202201");
        return String.valueOf(map.size());
    }

}
