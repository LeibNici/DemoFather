package com.maintest;

import com.SpringDataBaseBak.DataBaseBakApplication;
import com.SpringDataBaseBak.domain.DataBaseBakConfig;
import com.SpringDataBaseBak.mapper.DataBaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.HashSet;

/**
 * @author chenming
 * @description
 * @create: 2022-02-08
 */
@SpringBootTest(classes = DataBaseBakApplication.class)
@Slf4j
public class mainTest {

    @Autowired
    private DataBaseMapper dataBaseMapper;

    @Autowired
    private DataBaseBakConfig dataBaseBakConfig;

    @Test
    public void test() {
        //todo 当配置文件中的包含还是排除选项为 包含 时 对指定库进行操作
        if (dataBaseBakConfig.isInclude()) {
            // 获取当前配置文件中需要操作的数据库列表
            HashSet<String> bakList = (HashSet<String>) dataBaseBakConfig.getBakList();

            // todo 检测 mysql.exe 和 mysqldump.exe 是否存在
            if (new File(dataBaseBakConfig.getMysqlPath() + DataBaseBakConfig.mysql_EXE).exists() && new File(dataBaseBakConfig.getMysqlPath() + DataBaseBakConfig.mysqldump_EXE).exists()) {

            } else {
                log.error("mysql.exe 或 mysqldump.exe 文件不存在！请重新检查目录 database.backup.mysqlPath ");
            }

        } else {         //todo 当配置文件中的包含还是排除选项为 排除 时 对所有库进行排除操作
            HashSet<String> dataBasesList = dataBaseMapper.showDataBases();
            HashSet<String> bakList = (HashSet<String>) dataBaseBakConfig.getBakList();
            bakList.addAll(dataBaseBakConfig.getDefaultExclude());
            dataBasesList.removeAll(bakList);
            dataBasesList.size();
        }
    }

}
