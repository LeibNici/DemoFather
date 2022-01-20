package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("remote_point")
public class RemotePoint implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private Double x;
    private Double y;
    private Date recordDate;

}
