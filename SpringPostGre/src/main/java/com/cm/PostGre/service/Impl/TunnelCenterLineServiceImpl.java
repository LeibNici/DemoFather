package com.cm.PostGre.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cm.PostGre.domain.TunnelCenterLine;
import com.cm.PostGre.mapper.TunnelCenterLineMapper;
import com.cm.PostGre.service.TunnelCenterLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenming
 * @description
 * @create: 2022-04-14
 */
@Service
public class TunnelCenterLineServiceImpl extends ServiceImpl<TunnelCenterLineMapper, TunnelCenterLine> implements TunnelCenterLineService {

    @Autowired
    private TunnelCenterLineMapper tunnelCenterLineMapper;

    @Override
    public String createGeom(TunnelCenterLine last, TunnelCenterLine current) {
        return tunnelCenterLineMapper.createGeom(last.getPositionX(),last.getPositionY(),current.getPositionX(),current.getPositionY());
    }
}
