package com.cm.PostGre.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cm.PostGre.domain.BusPointDO;
import com.cm.PostGre.domain.BusRegion;
import com.cm.PostGre.domain.TunnelCenterLine;
import com.cm.PostGre.mapper.BusRegionMapper;
import com.cm.PostGre.service.BusRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author chenming
 * @description
 * @create: 2022-04-14
 */
@Service
public class BusRegionServiceImpl extends ServiceImpl<BusRegionMapper, BusRegion> implements BusRegionService {

    @Autowired
    private BusRegionMapper busRegionMapper;

    @Override
    public int insert(String regionName,String regionId, TunnelCenterLine last, TunnelCenterLine current) {
        return busRegionMapper.insertBusRegion(regionName,regionId, last, current);
    }

    @Override
    public List<BusPointDO> queryPassPoint(Double x1, Double y1, Double x2, Double y2, Double width) {
        return busRegionMapper.queryPassPoint(BigDecimal.valueOf(x1), BigDecimal.valueOf(y1), BigDecimal.valueOf(x2), BigDecimal.valueOf(y2), BigDecimal.valueOf(width));
    }

    @Override
    public String queryRegionName(BigDecimal x1, BigDecimal y1, BigDecimal width) {
        return busRegionMapper.queryRegionName(x1,y1,width);
    }

    @Override
    public BusPointDO pointCorrection(BigDecimal x1, BigDecimal y1, BigDecimal width) {
        return busRegionMapper.pointCorrection(x1,y1,width);
    }


}
