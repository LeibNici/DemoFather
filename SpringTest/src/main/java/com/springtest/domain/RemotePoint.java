package com.springtest.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.awt.geom.Point2D;
import java.util.Date;

@Data
@TableName("remote_point")
public class RemotePoint {

    private int id;
    private Double x;
    private Double y;
    private Date recordDate;

}
