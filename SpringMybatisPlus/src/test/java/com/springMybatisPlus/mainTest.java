package com.springMybatisPlus;

import com.mysql.cj.log.Log;
import com.springMybatisPlus.domain.Father;
import com.springMybatisPlus.mapper.TableUtils;
import com.springMybatisPlus.service.FatherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    public void test(){
        log.info(String.valueOf(fatherService.list().size()));
    }

    @Test
    public void test1(){
        List<Father> fathers = fatherService.selectFatherDetails();
        log.info(String.valueOf(fathers.isEmpty()));
    }

}
