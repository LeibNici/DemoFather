package com.SpringPoints.Service;

import com.SpringPoints.domain.Route;
import com.SpringPoints.mapper.RouteMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author chenming
 * @description 路线图
 * @create: 2022-01-24
 */
@Service
public class RouteServiceImpl extends ServiceImpl<RouteMapper, Route> implements RouteService {
}
