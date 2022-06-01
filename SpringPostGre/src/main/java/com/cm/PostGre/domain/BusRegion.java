package com.cm.PostGre.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chenming
 * @description
 * @create: 2022-04-14
 */
@Data
@TableName("bus_region")
public class BusRegion {
    private Long id;
    private String regionId;
    private String regionName;
    private String geom;
    private BigDecimal startX;
    private BigDecimal startY;
    private BigDecimal endX;
    private BigDecimal endY;
}
