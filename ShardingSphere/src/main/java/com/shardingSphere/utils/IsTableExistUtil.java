package com.shardingSphere.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shardingSphere.mapper.TableCheckMapper;
import com.shardingSphere.mapper.UserShardingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class IsTableExistUtil {

    @Autowired
    private TableCheckMapper tableCheckMapper;

    private static IsTableExistUtil isTableExistUtil;

    @PostConstruct
    public void init(){
        isTableExistUtil = this;
        isTableExistUtil.tableCheckMapper = this.tableCheckMapper;
    }

    public static boolean checkTable(String tableName) {
        if (isTableExistUtil.tableCheckMapper.tableCheck(tableName)==null){
            return false;
        }else {
            return true;
        }
    }
}
