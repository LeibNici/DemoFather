package com.cm.PostGre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface TunnelCenterLineMapper extends BaseMapper<TunnelCenterLine> {

    @Select("SELECT st_makeline(st_makepoint(#{startX}, #{startY}),st_makepoint(#{endX}, #{endY})) AS geom")
    public String createGeom(BigDecimal startX, BigDecimal startY, BigDecimal endX, BigDecimal endY);

}
