package com.springMybatisPlus.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springMybatisPlus.domain.BusRouteMonitoringPoints;
import com.springMybatisPlus.mapper.BusRouteMonitoringPointsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 监测点Service业务层处理
 *
 * @author chenming
 * @date 2022-03-16
 */
@Service
@Slf4j
public class BusRouteMonitoringPointsServiceImpl extends ServiceImpl<BusRouteMonitoringPointsMapper, BusRouteMonitoringPoints> implements IBusRouteMonitoringPointsService {

}
