package com.shardingSphere.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Driver {

    private int id;
    private String name;
    private String dept;
    private Date create_time;

}
