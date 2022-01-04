package com.shardingSphere.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TableCheckMapper {

    public String tableCheck(String tableName);

    public void executeSql(String sql);
}
