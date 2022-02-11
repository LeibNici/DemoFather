package com.maintest;

import com.SpringDataBaseBak.DataBaseBakApplication;
import com.SpringDataBaseBak.domain.DataBaseBakConfig;
import com.SpringDataBaseBak.mapper.DataBaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.text.SimpleDateFormat;
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

    private SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

    @Test
    public void test() {
        //todo 当配置文件中的包含还是排除选项为 包含 时 对指定库进行操作
        if (dataBaseBakConfig.isInclude()) {
            // 获取当前配置文件中需要操作的数据库列表
            HashSet<String> bakList = (HashSet<String>) dataBaseBakConfig.getBakList();

            // todo 检测 mysql.exe 和 mysqldump.exe 是否存在
            if (new File(dataBaseBakConfig.getMysqlPath() + DataBaseBakConfig.mysql_EXE).exists() && new File(dataBaseBakConfig.getMysqlPath() + DataBaseBakConfig.mysqldump_EXE).exists()) {

                //todo 使用 mysqldump 对列表库进行备份
                String mysqldump_CMD = dataBaseBakConfig.getMysqlPath() + DataBaseBakConfig.mysqldump_EXE;
                String target_username_CMD = "-u" + dataBaseBakConfig.getTargetUsername();
                String target_password_CMD = "-p" + dataBaseBakConfig.getTargetPassword();
                String target_host_CMD = "-h" + dataBaseBakConfig.getTargetHost();

                bakList.forEach(database -> {
                    String tartget_dataBase_CMD = "--databases " + database;
                    String execute_CMD = "\"" + mysqldump_CMD + "\"" + " " + target_host_CMD + " " + target_username_CMD + " " + target_password_CMD + " " + tartget_dataBase_CMD;
                    log.info(execute_CMD);
                });


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

    @Test
    public void test2() {
        // todo 检测 mysql.exe 和 mysqldump.exe 是否存在
        if (new File(dataBaseBakConfig.getMysqlPath() + DataBaseBakConfig.mysql_EXE).exists() && new File(dataBaseBakConfig.getMysqlPath() + DataBaseBakConfig.mysqldump_EXE).exists()) {

            HashSet<String> bakList = (HashSet<String>) dataBaseBakConfig.getBakList();

            //todo 使用 mysqldump 对列表库进行备份
            String mysqldump_CMD = dataBaseBakConfig.getMysqlPath() + DataBaseBakConfig.mysqldump_EXE;
            String target_username_CMD = "-u" + dataBaseBakConfig.getTargetUsername();
            String target_password_CMD = "-p" + dataBaseBakConfig.getTargetPassword();
            String target_host_CMD = " -h " + dataBaseBakConfig.getTargetHost();

            //todo 当配置文件中的包含还是排除选项为 包含 时 对指定库进行操作
            if (dataBaseBakConfig.isInclude()) {

                String date = sdf.format(System.currentTimeMillis());

                //如果文件夹不存在 则创建
                File today_bak = new File(dataBaseBakConfig.getFilePath() + "\\" + date);
                if (!today_bak.isDirectory()) {
                    today_bak.mkdirs();
                }

                bakList.forEach(database -> {
                    String tartget_dataBase_CMD = " --databases " + database;
                    String execute_CMD = "\"" + mysqldump_CMD + "\"" + target_host_CMD + " " + target_username_CMD + " " + target_password_CMD + tartget_dataBase_CMD + " > " + today_bak.getPath() + "\\" + database + ".sql";
                    log.info(execute_CMD);
                });

            } else { //todo 当配置文件中的包含还是排除选项为 排除 时 对所有库进行排除操作
                HashSet<String> dataBasesList = dataBaseMapper.showDataBases();
                bakList.addAll(dataBaseBakConfig.getDefaultExclude());
                dataBasesList.removeAll(bakList);
                dataBasesList.size();
            }

        } else {
            log.error("mysql.exe 或 mysqldump.exe 文件不存在！请重新检查目录 database.backup.mysqlPath ");
        }

    }

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;



    @Test
    public void test3() throws SchedulerException {
    }

}
