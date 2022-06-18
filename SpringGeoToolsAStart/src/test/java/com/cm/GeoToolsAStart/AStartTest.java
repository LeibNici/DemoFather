package com.cm.GeoToolsAStart;

import com.cm.GeoToolsAStart.Utils.GeometryUtil;
import com.cm.GeoToolsAStart.mapper.BusRegionMapper;
import lombok.extern.slf4j.Slf4j;
import org.geotools.data.DataUtilities;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.graph.build.feature.FeatureGraphGenerator;
import org.geotools.graph.build.line.LineStringGraphGenerator;
import org.geotools.graph.path.AStarShortestPathFinder;
import org.geotools.graph.path.Path;
import org.geotools.graph.structure.Edge;
import org.geotools.graph.structure.Graph;
import org.geotools.graph.structure.Node;
import org.geotools.graph.traverse.standard.AStarIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import javax.annotation.PostConstruct;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * @author chenming
 * @description
 * @create: 2022-06-17
 */
@SpringBootTest
@Slf4j
public class AStartTest {

    @Autowired
    private BusRegionMapper busRegionMapper;

    private static Graph graph = null;

    public void init() throws SchemaException {
        List<String> wkts = busRegionMapper.selectList();
        graph = GeometryUtil.createGraph(wkts);
    }

    @RepeatedTest(10)
    public void test1() throws Exception {
        init();
        Node start = graph.getNodes().stream().filter(node -> node.getID() == 574).findFirst().orElse(null);
        Node end = graph.getNodes().stream().filter(node -> node.getID() == 288).findFirst().orElse(null);
        long startT = System.currentTimeMillis();
        List<Point2D> nodes = GeometryUtil.AStarShortestPath(start,end,graph);
        log.error("jvm耗时:{}ms", System.currentTimeMillis() - startT);
        log.info(String.valueOf(nodes.size()));
    }

    public static Path searchPathByAstar(Node source, Node destination) throws Exception {
        if (graph == null) {
            System.out.println("graph不存在，请构建graph");
            return null;
        }
        if (source.equals(destination)) {
            System.out.println("起点和终点相同，请重新选点");
            return null;
        }

        AStarIterator.AStarFunctions aStarFunctions = new AStarIterator.AStarFunctions(destination) {

            @Override
            public double cost(AStarIterator.AStarNode start, AStarIterator.AStarNode end) {
                Edge edge = start.getNode().getEdge(end.getNode());
                SimpleFeature feature = (SimpleFeature) edge.getObject();
                Geometry geometry = (Geometry) feature.getDefaultGeometry();
                return geometry.getLength();
            }

            @Override
            public double h(Node node) {
                return 0;
            }
        };



        AStarShortestPathFinder pathFinder = new AStarShortestPathFinder(graph, source, destination, aStarFunctions);
        pathFinder.calculate();
        return pathFinder.getPath();
    }

    @Test
    public void test2() throws ParseException {
        LineString lineString = (LineString) GeometryUtil.readWKT("LINESTRING(0 0,3 3)");
        Point point = GeometryUtil.createPoint(1D, 0D);
        Point2D closeLinePoint = GeometryUtil.findCloseLinePoint(point, lineString);
        log.info("123");
    }

    @Test
    public void test3() throws ParseException, FactoryException, TransformException {
        LineString lineString = (LineString) GeometryUtil.readWKT("LINESTRING(7784.1267132104485 6279.310851037608,6579.9837812996075 5371.945302330972)");
        LineString geometry = (LineString) GeometryUtil.CRS_Transform("EPSG:3857", "EPSG:4326", lineString);
        log.info("123");
    }
}
