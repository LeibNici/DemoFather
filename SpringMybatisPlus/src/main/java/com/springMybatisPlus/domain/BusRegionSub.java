package com.springMybatisPlus.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chenming
 * @description
 * @create: 2022-03-16
 */
@Data
public class BusRegionSub {

    // 子区域id
    private int subId;
    // 父区域关联id
    private int regionId;
    // 子区域坐标x
    private BigDecimal subRegionX;
    // 子区域坐标y
    private BigDecimal subRegionY;

}
