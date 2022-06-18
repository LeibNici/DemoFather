package com.cm.PostGre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cm.PostGre.domain.TopologyRel;
import com.cm.PostGre.domain.TunnelCenterLine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * @author chenming
 * @description
 * @create: 2022-04-14
 */
@Mapper
public interface TopologyRelMapper extends BaseMapper<TopologyRel> {

}
