package com.recordPoint.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.recordPoint.domain.Route;
import com.recordPoint.mapper.RouteMapper;
import org.springframework.stereotype.Service;

/**
 * @author chenming
 * @description 路线图
 * @create: 2022-01-24
 */
@Service
public class RouteServiceImpl extends ServiceImpl<RouteMapper, Route> implements RouteService {
}
