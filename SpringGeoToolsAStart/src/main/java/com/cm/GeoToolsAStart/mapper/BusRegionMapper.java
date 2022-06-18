package com.cm.GeoToolsAStart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cm.GeoToolsAStart.domain.BusRegion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BusRegionMapper extends BaseMapper<BusRegion> {

    @Select("SELECT st_astext(geom) as wkt FROM bus_region")
    public List<String> selectList();

}
