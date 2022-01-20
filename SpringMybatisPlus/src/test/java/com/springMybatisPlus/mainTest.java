package com.springMybatisPlus;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mysql.cj.log.Log;
import com.springMybatisPlus.domain.Child;
import com.springMybatisPlus.domain.Father;
import com.springMybatisPlus.mapper.TableUtils;
import com.springMybatisPlus.service.ChildServiceImpl;
import com.springMybatisPlus.service.FatherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class mainTest {

    @Autowired
    private TableUtils tableUtils;

    @Test
    public void setTableUtils(){
        Map<Object, Object> map = tableUtils.showCreateTableSql("bus_car_operation");
        map.size();
    }

    @Autowired
    private FatherServiceImpl fatherService;

    @Autowired
    private ChildServiceImpl childService;

    @Test
    public void test(){
        log.info(String.valueOf(fatherService.list().size()));
    }

    @Test
    public void test1(){
        List<Father> fathers = fatherService.selectFatherDetails();
        log.info(String.valueOf(fathers.isEmpty()));
    }

    @Test
    public void test2(){
        UpdateWrapper<Child> s = new UpdateWrapper<>();
        Child child = new Child();
        child.setName("32");
        List<Integer> ids = Arrays.asList(1,2);
        s.in("id",ids);
    }

}
