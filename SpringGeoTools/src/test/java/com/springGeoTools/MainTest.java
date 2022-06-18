package com.springGeoTools;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.FeatureCollection;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.algorithm.distance.DistanceToPoint;
import org.locationtech.jts.algorithm.distance.PointPairDistance;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.linearref.LinearLocation;
import org.locationtech.jts.linearref.LocationIndexedLine;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.awt.Point;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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

    private String geoJson = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.014763,0.05991],[0.057077,0.052443],[0.047979,0.046177]]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[0.010386,0.029268],[0.010386,0.047894],[0.023775,0.047894],[0.023775,0.029268],[0.010386,0.029268]]]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.035319,0.033345],[0.035105,0.025277]]}}]}";

    @Test
    void test5() throws IOException {
        String geoJson = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.014763,0.05991],[0.057077,0.052443],[0.047979,0.046177]]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[0.010386,0.029268],[0.010386,0.047894],[0.023775,0.047894],[0.023775,0.029268],[0.010386,0.029268]]]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.035319,0.033345],[0.035105,0.025277]]}}]}";
        GeometryJSON geometryJSON = new GeometryJSON(8);
        Geometry read = geometryJSON.read(new StringReader(geoJson));
        log.info("123");
    }

    @Test
    void test66() throws IOException {
        FeatureJSON featureJSON = new FeatureJSON(new GeometryJSON(8));
        FeatureCollection featureCollection = featureJSON.readFeatureCollection(geoJson);
        SimpleFeatureIterator features = (SimpleFeatureIterator) featureCollection.features();
        while (features.hasNext()) {
            SimpleFeature next = features.next();
            Geometry geometry = (Geometry) next.getDefaultGeometry();
            log.info("123");
        }
    }

    @Test
    void test67() throws IOException, FactoryException, TransformException {
        CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:3857");
        CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:4326");
        MathTransform mathTransform = CRS.findMathTransform(sourceCRS, targetCRS,true);

        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        Coordinate c1 = new Coordinate(3662.7893,5372.0141);
        Coordinate c2 = new Coordinate(3662.7893,5372.0141);
        Coordinate[] coordinates1 = new Coordinate[]{c1,c2};
        LineString lineString = geometryFactory.createLineString(coordinates1);
        LineString dis = (LineString) JTS.transform(lineString,mathTransform);
        Coordinate[] coordinates = dis.getCoordinates();
        log.info("123");
    }

    @Test
    void test6(){

        log.info("123");
    }
}
