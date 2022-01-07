package com.springMybatisPlus.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface TableUtils {

    public Map<Object,Object> showCreateTableSql(String tableName);

}
