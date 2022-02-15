package com.maintest;

import com.SpringDataBaseBak.DataBaseBakApplication;
import com.SpringDataBaseBak.utils.cmdUtils;
import com.SpringDataBaseBak.domain.DataBaseBakConfig;
import com.SpringDataBaseBak.mapper.DataBaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;

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

    @Autowired
    private cmdUtils cmdUtils;

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

                long before = System.currentTimeMillis();
                bakList.forEach(database -> {

                    String tartget_dataBase_CMD = " --databases " + database;
                    String execute_CMD = "\"" + mysqldump_CMD + "\"" + target_host_CMD + " " + target_username_CMD + " " + target_password_CMD + tartget_dataBase_CMD + " > " + today_bak.getPath() + "\\" + database + ".sql";
                    log.info(execute_CMD);
//                    try {
//                        cmdUtils.excute(today_bak, execute_CMD);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

                });
                long after = System.currentTimeMillis();

                log.info("耗时：{}", after - before);

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

    @Test
    public void test3() {
        String cmd1 = "\"D:\\Program Files (x86)\\mysql-8.0.26-winx64\\bin\\mysqldump.exe\" -h 127.0.0.1 -uroot -p123456 --databases uwb_loc > D:\\tmp\\20220211\\uwb_loc.sql";
        String cmd2 = "\"D:\\Program Files (x86)\\mysql-8.0.26-winx64\\bin\\mysqldump.exe\" -h 127.0.0.1 -uroot -p123456 --databases fzys > D:\\tmp\\20220211\\fzys.sql";

        List<String> cmdList = new ArrayList<>();
        cmdList.add(cmd1);
        cmdList.add(cmd2);

        String date = sdf.format(System.currentTimeMillis());

        //如果文件夹不存在 则创建
        File today_bak = new File(dataBaseBakConfig.getFilePath() + "\\" + date);
        if (!today_bak.isDirectory()) {
            today_bak.mkdirs();
        }


        long before = System.currentTimeMillis();

        cmdList.forEach(s -> {
            try {
                cmdUtils.excute(today_bak, s);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long after = System.currentTimeMillis();
        log.info("耗时：{}", after - before);

    }

}
