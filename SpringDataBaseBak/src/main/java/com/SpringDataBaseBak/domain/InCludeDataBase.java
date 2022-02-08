package com.SpringDataBaseBak.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author chenming
 * @description
 * @create: 2022-02-08
 */
@ConfigurationProperties(prefix = "databak")
@RefreshScope
@Component
public class InCludeDataBase {

    private String mysqlPath;
    private String IncludeOrExclude = "include";
    private Set<String> BakList;

    public String getMysqlPath() {
        return mysqlPath;
    }

    public void setMysqlPath(String mysqlPath) {
        this.mysqlPath = mysqlPath;
    }

    public String getIncludeOrExclude() {
        return IncludeOrExclude;
    }

    public void setIncludeOrExclude(String includeOrExclude) {
        IncludeOrExclude = includeOrExclude;
    }

    public Set<String> getBakList() {
        return BakList;
    }

    public void setBakList(Set<String> bakList) {
        BakList = bakList;
    }
}
