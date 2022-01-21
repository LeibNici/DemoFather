package com.springUpload.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenming
 * @description 解析配置文件上传路径
 * @create: 2022-01-20
 */
@Configuration
@ConfigurationProperties(prefix = "file")
public class FilePath {

    private String path;

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }
}
