package com.shardingSphere.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 * 车辆运行记录对象 bus_car_operation
 *
 * @author mxg
 * @date 2021-02-23
 */
@Data
public class BusCarOperation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 地点x坐标
     */
    private String siteX;

    /**
     * 地点y坐标
     */
    private String siteY;

    /**
     * 记录时间
     */
    private Date recordDate;
}
