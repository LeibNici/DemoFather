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
@TableName("t_tunnel_center_line")
public class TunnelCenterLine implements Comparable<TunnelCenterLine> {
    private String regionName;
    private String regionId;
    private BigDecimal positionX;
    private BigDecimal positionY;
    private String guid;
    private Long sort;

    @Override
    public int compareTo(TunnelCenterLine o) {
        return this.sort.compareTo(o.getSort());
    }
}
