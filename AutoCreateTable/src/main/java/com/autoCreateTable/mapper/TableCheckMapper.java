package com.autoCreateTable.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface TableCheckMapper {

    public Map<Object,Object> showCreateSql(String sql);

}
