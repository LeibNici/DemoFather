package com.SpringDruid.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * @author chenming
 * @description
 * @create: 2022-06-07
 */
@Configuration
@Slf4j
public class InitDataBase {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        Resource resource = new ClassPathResource("sql/init.sql");
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(resource);
        resourceDatabasePopulator.execute(dataSource);
    }
}
