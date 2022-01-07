package com.shardingSphere.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface TableCheckMapper {

    public String tableCheck(String tableName);

    public void executeSql(String sql);

    public Map<Object, Object> showCreateTbaleSql(String tableName);
}
