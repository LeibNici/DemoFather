package com.cm.PostGre;

import com.cm.PostGre.domain.BusPointDO;
import com.cm.PostGre.domain.BusRegion;
import com.cm.PostGre.domain.BusRegionGeoJsonDO;
import com.cm.PostGre.domain.TunnelCenterLine;
import com.cm.PostGre.mapper.BusRegionMapper;
import com.cm.PostGre.service.BusRegionService;
import com.cm.PostGre.service.TunnelCenterLineService;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import lombok.extern.slf4j.Slf4j;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.sampled.Line;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenming
 * @description
 * @create: 2022-04-14
 */
@SpringBootTest
@Slf4j
public class TestMain {
    @Autowired
    private TunnelCenterLineService tunnelCenterLineService;
    @Autowired
    private BusRegionService busRegionService;
    @Autowired
    private BusRegionMapper busRegionMapper;

    @Test
    void test1() {
        List<TunnelCenterLine> tunnelCenterLines = busRegionMapper.selectList();
        Map<String, List<TunnelCenterLine>> regionMap = tunnelCenterLines.stream().collect(Collectors.groupingBy(TunnelCenterLine::getRegionId));
        for (String key : regionMap.keySet()) {
            List<TunnelCenterLine> tmp = regionMap.get(key);
            tmp.sort(Comparator.comparing(TunnelCenterLine::getSort));
            for (int i = 1; i < tmp.size(); i++) {
                busRegionService.insert(tmp.get(i).getRegionName(),key,tmp.get(i-1),tmp.get(i));
            }
        }
        log.info("123");
    }

    @Test
    void test2() {
        Point2D.Double start = new Point2D.Double(37418433.42, 4381666.51);
        Point2D.Double end = new Point2D.Double(37418299.48, 4381542.18);
        long l = System.currentTimeMillis();
        List<BusPointDO> busPassPointDOList = busRegionService.queryPassPoint(start.getX(), start.getY(), end.getX(), end.getY(), 12D);
        busPassPointDOList.removeAll(Collections.singleton(null));
        log.info(String.valueOf(System.currentTimeMillis() - l));
    }

    @Test
    void test3() throws ParseException, IOException {
        List<BusRegionGeoJsonDO> list = busRegionMapper.selectGeoJson();

        GeometryJSON gjson = new GeometryJSON();


        String pointString = "{\"type\":\"Point\",\"coordinates\":[37410127.115,4382372.84]}";

        log.info("123");
    }

    @Test
    void test4(){
//        fzys:carArea:安CR2003
        String s = "fzys:carArea:安CR2003";
        String pre = "fzys:carArea:";
        System.out.println(s.substring(pre.length()));
    }

    @Test
    void test5(){

    }
}
