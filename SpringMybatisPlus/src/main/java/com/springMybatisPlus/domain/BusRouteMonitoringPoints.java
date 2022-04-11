package com.springMybatisPlus.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 监测点对象 bus_route_monitoring_points
 *
 * @author chenming
 * @date 2022-03-16
 */
@Data
public class BusRouteMonitoringPoints{
    /**
     * 编号
     */
    private int id;

    /**
     * 该区域内点位标识
     */
    private int identifies;

    /**
     * 坐标x
     */
    private BigDecimal pointX;

    /**
     * 坐标y
     */
    private BigDecimal pointY;

    /**
     * 区域关联id
     */
    private int regionId;
}
