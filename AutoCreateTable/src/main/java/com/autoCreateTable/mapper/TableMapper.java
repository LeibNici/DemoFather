package com.autoCreateTable.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface TableMapper {

    public Map<String,String> showCreateTableSql(String tableName);

    public int createTable(String createSql);
}
