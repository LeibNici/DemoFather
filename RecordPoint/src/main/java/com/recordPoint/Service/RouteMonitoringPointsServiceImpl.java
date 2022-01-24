package com.recordPoint.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.recordPoint.domain.Route;
import com.recordPoint.domain.RouteMonitoringPoints;
import com.recordPoint.mapper.RouteMapper;
import com.recordPoint.mapper.RouteMonitoringPointsMapper;
import org.springframework.stereotype.Service;

/**
 * @author chenming
 * @description 路线图
 * @create: 2022-01-24
 */
@Service
public class RouteMonitoringPointsServiceImpl extends ServiceImpl<RouteMonitoringPointsMapper, RouteMonitoringPoints> implements RouteMonitoringPointsService {
}
