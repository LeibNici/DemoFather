package com.cacheRedis.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
public class User {

    private String username;

    private Integer age;

    private Date date;
}
