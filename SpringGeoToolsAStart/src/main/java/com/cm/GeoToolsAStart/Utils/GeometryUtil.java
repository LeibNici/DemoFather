package com.cm.GeoToolsAStart.Utils;

import org.geotools.data.DataUtilities;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.graph.build.feature.FeatureGraphGenerator;
import org.geotools.graph.build.line.LineStringGraphGenerator;
import org.geotools.graph.path.AStarShortestPathFinder;
import org.geotools.graph.path.Path;
import org.geotools.graph.structure.Edge;
import org.geotools.graph.structure.Graph;
import org.geotools.graph.structure.Node;
import org.geotools.graph.traverse.standard.AStarIterator;
import org.geotools.referencing.CRS;
import org.locationtech.jts.algorithm.distance.DistanceToPoint;
import org.locationtech.jts.algorithm.distance.PointPairDistance;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.springframework.context.annotation.Primary;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author chenming
 * @description
 * @create: 2022-06-17
 */
public class GeometryUtil {

    private static final WKTReader wktReader = new WKTReader();
    private static final GeometryJSON geometryJSON = new GeometryJSON(10);
    private static final GeometryFactory GEOMETRY_FACTORY = JTSFactoryFinder.getGeometryFactory();
    private static final double D = 1;
    private static SimpleFeatureType featureType = null;
    private static final LineStringGraphGenerator lineStringGraphGenerator = new LineStringGraphGenerator();
    private static final FeatureGraphGenerator featureGen = new FeatureGraphGenerator(lineStringGraphGenerator);
    private static final GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
    private static MathTransform mathTransform = null;

    private static final String type = "LineString";

    static {
        try {
            featureType = DataUtilities.createType("Link", "geometry:" + type);
        } catch (SchemaException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取wkt返回空间结构
     *
     * @param wkt wkt数据
     * @return
     * @throws ParseException
     */
    public static Geometry readWKT(String wkt) throws ParseException {
        return wktReader.read(wkt);
    }

    /**
     * 读取geojson返回空间结构
     *
     * @param geoJson geojson数据
     * @return
     * @throws IOException
     */
    public static Geometry readGeoJson(String geoJson) throws IOException {
        return geometryJSON.read(geoJson);
    }

    /**
     * 根据一个点创建空间点
     *
     * @param x x坐标
     * @param y y坐标
     * @return
     */
    public static Point createPoint(Double x, Double y) {
        return GEOMETRY_FACTORY.createPoint(new Coordinate(x, y));
    }

    /**
     * 根据两个点返回空间线
     *
     * @param x1 x1坐标
     * @param y1 y1坐标
     * @param x2 x2坐标
     * @param y2 y2坐标
     * @return
     */
    public static LineString createLineString(Double x1, Double y1, Double x2, Double y2) {
        Coordinate p1 = new Coordinate(x1, y1);
        Coordinate p2 = new Coordinate(x2, y2);
        Coordinate[] coordinates = new Coordinate[]{p1, p2};
        return GEOMETRY_FACTORY.createLineString(coordinates);
    }

    /**
     * 查找距离查找点最近的线
     *
     * @param point            查找点
     * @param FuzzyLookupRange 模糊查找范围
     * @param lineStringList   线集合
     * @return
     */
    public static LineString findNearestLine(Point2D.Double point, Double FuzzyLookupRange, List<LineString> lineStringList) {
        Point sourcePoint = createPoint(point.getX(), point.getY());
        LineString result = lineStringList.parallelStream().filter(lineString -> lineString.buffer(FuzzyLookupRange).contains(sourcePoint)).min(new Comparator<LineString>() {
            @Override
            public int compare(LineString o1, LineString o2) {
                Double o1Dis = o1.distance(sourcePoint);
                Double o2Dis = o2.distance(sourcePoint);
                return o1Dis.compareTo(o2Dis);
            }
        }).orElse(null);
        return result;
    }

    /**
     * 查找点在已知线段上的投影点
     *
     * @param sourcePoint 查找点
     * @param lineString  目标线
     * @return
     */
    public static Point2D findCloseLinePoint(Point sourcePoint, LineString lineString) {
        Coordinate point = new Coordinate(sourcePoint.getX(), sourcePoint.getY());
        PointPairDistance pointPairDistance = new PointPairDistance();
        DistanceToPoint.computeDistance(lineString, point, pointPairDistance);
        Coordinate result = pointPairDistance.getCoordinate(0);
        return new Point2D.Double(result.getX(), result.getY());
    }

    /**
     * AStart 最短路径算法
     *
     * @param start 开始点
     * @param end   结束点
     * @param graph 无向图
     * @return
     * @throws Exception
     */
    public static List<Point2D> AStarShortestPath(Node start, Node end, Graph graph) throws Exception {
        List<Point2D> pathPoint = new ArrayList<>();
        Point endPoint = (Point) end.getObject();
        if (graph == null) {
            System.out.println("graph不存在，请构建graph");
            return pathPoint;
        }
        if (start.equals(end)) {
            System.out.println("起点和终点相同，请重新选点");
            return pathPoint;
        }
        AStarIterator.AStarFunctions aStarFunctions = new AStarIterator.AStarFunctions(end) {
            @Override
            public double cost(AStarIterator.AStarNode start, AStarIterator.AStarNode end) {
                Edge edge = start.getNode().getEdge(end.getNode());
                SimpleFeature feature = (SimpleFeature) edge.getObject();
                Geometry geometry = (Geometry) feature.getDefaultGeometry();
                return geometry.getLength();
            }

            @Override
            public double h(Node node) {
                Point nodePoint = (Point) node.getObject();
                double dx = Math.abs(nodePoint.getX() - endPoint.getX());
                double dy = Math.abs(nodePoint.getY() - endPoint.getY());
                return D * (dx + dy);
            }
        };
        AStarShortestPathFinder pathFinder = new AStarShortestPathFinder(graph, start, end, aStarFunctions);
        pathFinder.calculate();
        Path path = pathFinder.getPath();
        path.forEach(node -> {
            Point nodeObject = (Point) node.getObject();
            Point2D.Double xyPoint = new Point2D.Double(nodeObject.getX(), nodeObject.getY());
            pathPoint.add(xyPoint);
        });
        return pathPoint;
    }

    /**
     * 创建图
     *
     * @param wkts 读取wkt数据
     * @return
     */
    public static Graph createGraph(List<String> wkts) {
        wkts.forEach(s -> {
            try {
                LineString geometry = (LineString) GeometryUtil.readWKT(s);
                SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(featureType);
                featureBuilder.add(geometry);
                SimpleFeature simpleFeature = featureBuilder.buildFeature(null);
                featureGen.add(simpleFeature);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        return featureGen.getGraph();
    }

    /**
     * 由 源CRS 转换投影为 目标CRS
     *
     * @param sourceCRS 源CRS
     * @param targetCRS 目标CRS
     * @param geometry  转换类
     * @return
     * @throws FactoryException
     * @throws TransformException
     */
    public static Geometry CRS_Transform(String sourceCRS, String targetCRS, Geometry geometry) throws FactoryException, TransformException {
        CoordinateReferenceSystem source = CRS.decode(sourceCRS);
        CoordinateReferenceSystem target = CRS.decode(targetCRS);
        mathTransform = CRS.findMathTransform(source, target);
        return JTS.transform(geometry, mathTransform);
    }

}
