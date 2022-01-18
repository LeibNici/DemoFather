package com.springMybatisPlus.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public class Father {

    private int id;

    private String father;

    @TableField(exist = false)
    private List<Child> childList;

}
