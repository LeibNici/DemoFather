package com.springMybatisPlus;

import com.springMybatisPlus.mapper.TableUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class mainTest {

    @Autowired
    private TableUtils tableUtils;

    @Test
    public void setTableUtils(){
        Map<Object, Object> map = tableUtils.showCreateTableSql("bus_car_operation");
        map.size();
    }

}
