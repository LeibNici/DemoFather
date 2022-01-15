package com.springtest.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 车辆运行记录对象 bus_car_operation
 *
 * @author mxg
 * @date 2021-02-23
 */
@Data
@TableName("bus_car_operation")
public class BusCarOperation {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @EqualsAndHashCode.Exclude
    private Long id;

    /**
     * 车牌号
     */
    private String carNum;

    /**
     * 地点x坐标
     */
    private String siteX;

    /**
     * 地点y坐标
     */
    private String siteY;

    /**
     * 区域编号
     */
    private String areaNum;

    /**
     * 记录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @EqualsAndHashCode.Exclude
    private Date recordDate;

    /**
     * 所在区域位置
     */
    private String areaLocation;

    private String cardNumber;
}
