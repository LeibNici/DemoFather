package com.springMybatisPlus.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_child")
public class Child {
    private int id;
    private int fatherId;
    private String name;
}
