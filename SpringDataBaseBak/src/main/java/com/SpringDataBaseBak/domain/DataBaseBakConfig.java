package com.SpringDataBaseBak.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author chenming
 * @description
 * @create: 2022-02-08
 */
@ConfigurationProperties(prefix = "database.backup")
@RefreshScope
@Component
public class DataBaseBakConfig {

    // mysql home
    private String mysqlPath;

    // mysql 目标host
    private String targetHost;

    // mysql 目标username
    private String targetUsername;

    // mysql 目标password
    private String targetPassword;

    // 备份时间周期
    private String schedule_cron;

    // 备份文件保留时间
    private String keepTime;

    // baklist是排除还是包括
    private boolean isInclude = true;

    // 备份列表
    private Set<String> BakList;

    // sql保存路径
    private String filePath;

    // 默认排除库
    private List<String> defaultExclude = new ArrayList<>(Arrays.asList("mysql", "information_schema", "sys", "performance_schema"));

    public static final String mysql_EXE = "\\mysql.exe";
    public static final String mysqldump_EXE = "\\mysqldump.exe";

    public String getMysqlPath() {
        return mysqlPath;
    }

    public void setMysqlPath(String mysqlPath) {
        this.mysqlPath = mysqlPath;
    }

    public String getTargetHost() {
        return targetHost;
    }

    public void setTargetHost(String targetHost) {
        this.targetHost = targetHost;
    }

    public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }

    public String getTargetPassword() {
        return targetPassword;
    }

    public void setTargetPassword(String targetPassword) {
        this.targetPassword = targetPassword;
    }

    public String getSchedule_cron() {
        return schedule_cron;
    }

    public void setSchedule_cron(String schedule_cron) {
        this.schedule_cron = schedule_cron;
    }

    public String getKeepTime() {
        return keepTime;
    }

    public void setKeepTime(String keepTime) {
        this.keepTime = keepTime;
    }

    public boolean isInclude() {
        return isInclude;
    }

    public void setInclude(boolean include) {
        isInclude = include;
    }

    public Set<String> getBakList() {
        return BakList;
    }

    public void setBakList(Set<String> bakList) {
        BakList = bakList;
    }

    public List<String> getDefaultExclude() {
        return defaultExclude;
    }

    public void setDefaultExclude(List<String> defaultExclude) {
        this.defaultExclude = defaultExclude;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
