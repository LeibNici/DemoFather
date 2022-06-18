package com.cm.PostGre.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cm.PostGre.domain.TopologyRel;
import com.cm.PostGre.domain.TunnelCenterLine;
import com.cm.PostGre.mapper.TopologyRelMapper;
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
public class TopologyRelServiceImpl extends ServiceImpl<TopologyRelMapper, TopologyRel> {
}
