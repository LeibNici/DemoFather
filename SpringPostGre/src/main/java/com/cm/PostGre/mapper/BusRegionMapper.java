package com.cm.PostGre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cm.PostGre.domain.BusPointDO;
import com.cm.PostGre.domain.BusRegion;
import com.cm.PostGre.domain.BusRegionGeoJsonDO;
import com.cm.PostGre.domain.TunnelCenterLine;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.checkerframework.checker.index.qual.SameLen;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author chenming
 * @description
 * @create: 2022-04-14
 */
@Mapper
public interface BusRegionMapper extends BaseMapper<BusRegion> {

    @Insert("INSERT INTO bus_region " +
            "(region_name, region_id, geom , start_x,start_y,end_x,end_y ) " +
            "VALUES " +
            "(#{regionName},#{regionId},(SELECT st_makeline(st_makepoint(#{last.positionX}, #{last.positionY}),st_makepoint(#{current.positionX}, #{current.positionY}))),#{last.positionX}, #{last.positionY},#{current.positionX}, #{current.positionY})")
    int insertBusRegion(@Param("regionName") String regionName, @Param("regionId") String regionId, @Param("last") TunnelCenterLine last, @Param("current") TunnelCenterLine current);

    @Select("SELECT * FROM cm_query_path(#{x1},#{y1},#{x2},#{y2},#{width})")
    List<BusPointDO> queryPassPoint(BigDecimal x1, BigDecimal y1, BigDecimal x2, BigDecimal y2, BigDecimal width);

    @Select("SELECT cm_query_region(#{x1},#{y1},#{width})")
    String queryRegionName(BigDecimal x1, BigDecimal y1, BigDecimal width);

    @Select("SELECT * FROM cm_point_correction(#{x1},#{y1},#{width})")
    BusPointDO pointCorrection(BigDecimal x1, BigDecimal y1, BigDecimal width);

    @Select("SELECT id, region_name, st_asgeojson(geom) as geo_json FROM bus_region")
    List<BusRegionGeoJsonDO> selectGeoJson();

    @Select("SELECT tt.name AS region_name , tt.id AS region_id ,cl.position_x,cl.position_y,cl.sort FROM t_tunnel_center_line cl LEFT JOIN t_tunnel tt ON cl.tunnel_id = tt.id WHERE tt.name IS NOT NULL")
    List<TunnelCenterLine> selectList();
}
