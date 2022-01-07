package com.autoCreateTable.utils;

import com.autoCreateTable.mapper.TableCheckMapper;
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
}
