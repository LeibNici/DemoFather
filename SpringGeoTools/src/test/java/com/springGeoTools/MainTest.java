package com.springGeoTools;

import com.vividsolutions.jts.algorithm.distance.DistanceToPoint;
import com.vividsolutions.jts.algorithm.distance.PointPairDistance;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.linearref.LinearLocation;
import com.vividsolutions.jts.linearref.LocationIndexedLine;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.junit.jupiter.api.Test;
import org.opengis.filter.capability.FunctionName;
import org.opengis.filter.expression.Expression;
import org.opengis.filter.expression.ExpressionVisitor;
import org.opengis.filter.expression.Function;
import org.opengis.filter.expression.Literal;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenming
 * @description
 * @create: 2022-04-21
 */
@SpringBootTest
@Slf4j
public class MainTest {

    @Test
    void test1() throws ParseException {
        WKTReader reader = new WKTReader(JTSFactoryFinder.getGeometryFactory());
        LineString line1 = (LineString) reader.read("LINESTRING(0 0, 10 0, 10 10, 20 10)");
        LineString line2 = (LineString) reader.read("LINESTRING(0 0, 10 0, 10 10, 20 10)");
        Coordinate c = new Coordinate(3, 5);
        PointPairDistance ppd = new PointPairDistance();
        DistanceToPoint.computeDistance(line1, c, ppd);
        System.out.println(ppd.getDistance());
        for (Coordinate cc : ppd.getCoordinates()) {
            System.out.println(cc);
        }
        List<LineString> lineList = new ArrayList<>(Arrays.asList(line1, line2));
    }

    @Data
    static class BusRegion {
        private String regionName;
        private BigDecimal startX;
        private BigDecimal startY;
        private BigDecimal endX;
        private BigDecimal endY;
    }


    @Test
    void test2() throws ParseException {
        WKTReader reader = new WKTReader(JTSFactoryFinder.getGeometryFactory());
        LocationIndexedLine line1 = new LocationIndexedLine(reader.read("LINESTRING(0 0, 0 3)"));
//        LocationIndexedLine line2 =  new LocationIndexedLine(reader.read("LINESTRING(11 0, 10 0, 10 10, 21 15)"));
        List<LocationIndexedLine> list = new ArrayList<>(Arrays.asList(line1));
        Coordinate pt = new Coordinate(1, 2);
        for (LocationIndexedLine locationIndexedLine : list) {
            LinearLocation here = locationIndexedLine.project(pt);
            Coordinate coordinate = locationIndexedLine.extractPoint(here);
            log.info("123");
        }
    }

    @Test
    void test3() {
        Point point1 = new Point(0, 0);
        Point point2 = new Point(0, 5);
        Point point3 = new Point(5, 10);
        List<Point> list = new ArrayList<>(Arrays.asList(point2,point1, point3));
        Point point = new Point(1, 1);
        List<Point> collect = list.stream().sorted().collect(Collectors.toList());
        log.info("123");
    }

    @Test
    void test4() throws ParseException {
        GeometryFactory gf = JTSFactoryFinder.getGeometryFactory(null);
        WKTReader reader = new WKTReader(gf);
        Geometry line2 = reader.read("LINESTRING(0 0, 0 10)");
        Coordinate c = new Coordinate(1, 3);
        PointPairDistance ppd = new PointPairDistance();
        DistanceToPoint.computeDistance(line2, c, ppd);
        System.out.println(ppd.getDistance());
        for (Coordinate cc : ppd.getCoordinates()) {
            System.out.println(cc);
        }

    }
}
