package com.springMybatisPlus.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenming
 * @description 车辆网络历史记录
 * @create: 2022-03-08
 */
@Data
@TableName("bus_vehicle_position_record20220324")
public class BusVehicleNetworkPositionRecord {

    // 编号
    private Long id;

    // 车辆定位卡号
    private int carLocationNum;

    // 定位X
    private BigDecimal locationX;

    // 定位Y
    private BigDecimal locationY;

    // 区域编号
    private int regionId;

    // 子区域点位编号
    private int identifies;

    // 网络信号质量
    @TableField(exist = false)
    private int networkSignalQuality;

    // 记录时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date recordDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(exist = false)
    private Date beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(exist = false)
    private Date endTime;
}
