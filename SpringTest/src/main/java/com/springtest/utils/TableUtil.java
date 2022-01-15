package com.springtest.utils;

import com.springtest.mapper.TableCheckMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class TableUtil {

    @Autowired
    private TableCheckMapper tableCheckMapper;

    private static TableUtil tableUtil;

    @PostConstruct
    public void init() {
        tableUtil = this;
        tableUtil.tableCheckMapper = this.tableCheckMapper;
    }

    public static boolean checkTable(String tableName) {
        if (tableUtil.tableCheckMapper.tableCheck(tableName) == null) {
            return false;
        } else {
            return true;
        }
    }

    public static String createTable(String date) {
        String tableName = "bus_car_operation_" + date;
        String sql = "CREATE TABLE " + tableName + " (\n" +
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
                "  KEY `car_date_x_y_cardNum` (`car_num`,`record_date`,`card_number`) USING BTREE,\n" +
                "  KEY `x` (`car_num`,`site_x`) USING BTREE\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=28974886 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='车辆运行记录表'";
        tableUtil.tableCheckMapper.executeSql(sql);
        return tableName;
    }

    public static Map<Object, Object> showCreateTable() {
        return tableUtil.tableCheckMapper.showCreateTbaleSql("bus_car_operation");
    }
}
