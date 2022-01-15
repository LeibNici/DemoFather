package com.springtest.mapper;

import com.springtest.domain.BusCarOperation_11;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TableCheckMapper {

    public String tableCheck(String tableName);

    public void executeSql(String sql);

    public List<BusCarOperation_11> selectSql(String sql);

    public Map<Object, Object> showCreateTbaleSql(String tableName);
}
