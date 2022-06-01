package com.shardingSphere;

import com.shardingSphere.mapper.TableCheckMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class mainTest {

    @Autowired
    private TableCheckMapper tableCheckMapper;

    @Test
    public void kstin(){
        Map<Object, Object> map = tableCheckMapper.showCreateTbaleSql("bus_car_operation");
        map.size();
    }

    @Test
    public void test1(){

    }

}
