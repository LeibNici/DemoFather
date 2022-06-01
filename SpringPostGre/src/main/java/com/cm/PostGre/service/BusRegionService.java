package com.cm.PostGre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cm.PostGre.domain.BusPointDO;
import com.cm.PostGre.domain.BusRegion;
import com.cm.PostGre.domain.TunnelCenterLine;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author chenming
 * @description
 * @create: 2022-04-14
 */
public interface BusRegionService extends IService<BusRegion> {
    int insert(String regionName,String regionId, TunnelCenterLine tunnelCenterLine, TunnelCenterLine tunnelCenterLine1);

    List<BusPointDO> queryPassPoint(Double x1, Double y1, Double x2, Double y2, Double width);

    String queryRegionName(BigDecimal x1, BigDecimal y1, BigDecimal width);

    BusPointDO pointCorrection(BigDecimal x1, BigDecimal y1, BigDecimal width);
}
