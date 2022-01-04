package com.shardingSphere.utils;

import com.shardingSphere.mapper.TableCheckMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        String tableName = "user_" + date;
        String sql = "CREATE TABLE " + tableName + " (\n" +
                "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `create_time` datetime DEFAULT NULL,\n" +
                "  `age` int DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`) USING BTREE\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=1022570499 DEFAULT CHARSET=utf8mb3";
        tableUtil.tableCheckMapper.executeSql(sql);
        return tableName;
    }
}
