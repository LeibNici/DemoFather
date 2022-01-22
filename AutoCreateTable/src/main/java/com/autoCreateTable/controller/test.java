package com.autoCreateTable;

import com.autoCreateTable.mapper.TableMapper;
import com.autoCreateTable.utils.TableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.beans.SimpleBeanInfo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@Slf4j
public class test {

    @GetMapping("/get")
    public Object test() {
        return "null";
    }

    @GetMapping("/insert")
    public Object test2() {
        // 车辆运行记录添加
        return null;
    }

}
