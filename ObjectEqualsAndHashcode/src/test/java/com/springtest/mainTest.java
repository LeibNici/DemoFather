package com.springtest;

import com.cm.domian.Driver;
import com.cm.domian.User;
import com.cm.domian.vo.DriverVo;
import com.cm.utils.AnalysisObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
@Slf4j
public class mainTest {

    @Test
    public void test() {
        Driver driver = new Driver();
        driver.setName("chenming");
        driver.setCity("qingdao");
        driver.setCountry("zhongguo");

        DriverVo driverVo = new DriverVo();
        BeanUtils.copyProperties(driver, driverVo);

        driver.getAge();
    }

    @Test
    public void test1() {

        Map<String, Object> map = new HashMap<>();

        List list1 = new LinkedList();
        for (int i = 0; i < 3; i++) {
            Driver driver = new Driver();
            driver.setName("chenming");
            driver.setCity("qingdao");
            driver.setCountry("zhongguo");
            list1.add(driver);
        }
        map.put("1", list1);

        List list2 = new LinkedList();
        for (int i = 4; i < 6; i++) {
            User user = new User();
            user.setUsername("chen");
            user.setDate(new Date());
            list2.add(user);
        }
        map.put("2", list2);
        AnalysisObject.analysis(map);
        Map<String, Class> result = AnalysisObject.map;

        log.info(String.valueOf(result.size()));

    }

}
