//package com.cm.mybatisHandler;
//
//import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//public class DynamicTableNameByDate implements TableNameHandler {
//
//    @Override
//    public String dynamicTableName(String sql, String tableName) {
//
//        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
//        return tableName + "_" + date;
//    }
//}
