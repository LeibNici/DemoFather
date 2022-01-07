package com.springtest;

import com.cm.domian.Driver;
import com.cm.domian.User;
import com.cm.domian.vo.DriverVo;
import com.cm.utils.AnalysisObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
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

    @Test
    public void test3() {
        String sourceSql = "CREATE TABLE `bus_car_operation` (\n" +
                "  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
                "  `car_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '车牌号',\n" +
                "  `site_x` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '地点x坐标',\n" +
                "  `site_y` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '地点y坐标',\n" +
                "  `area_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '区域编号',\n" +
                "  `record_date` datetime DEFAULT NULL COMMENT '记录时间',\n" +
                "  `area_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '所在区域位置',\n" +
                "  `card_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '车辆定位卡号',\n" +
                "  PRIMARY KEY (`id`) USING BTREE,\n" +
                "  UNIQUE KEY `id` (`id`) USING BTREE,\n" +
                "  KEY `car_date_x_y_cardNum` (`car_num`,`record_date`,`card_number`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='车辆运行记录表'";

        String table_name = "bus_car_operation_" + new SimpleDateFormat("YYYYMM").format(new Date());

        String targetSql = sourceSql.replace("bus_car_operation", table_name);
        log.info(targetSql);

    }

}
