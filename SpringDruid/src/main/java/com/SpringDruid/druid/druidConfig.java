package com.SpringDruid.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author chenming
 * @description 数据库连接
 * @create: 2022-01-24
 */
@Configuration
public class druidConfig {

    @Bean(name = "dataSource")
    public DataSource druidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/realypoint");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }

}
