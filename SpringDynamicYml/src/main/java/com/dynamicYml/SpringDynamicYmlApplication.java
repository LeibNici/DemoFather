package com.dynamicYml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * @author chenming
 * @description
 * @create: 2022-01-27
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringDynamicYmlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDynamicYmlApplication.class);
    }

}
