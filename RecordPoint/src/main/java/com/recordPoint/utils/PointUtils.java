package com.recordPoint.utils;

import sun.util.resources.ga.LocaleNames_ga;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenming
 * @description 点位工具类
 * @create: 2022-01-21
 */
public class PointUtils {

    /**
     * 给指定起点和终点，返回等距点位
     *
     * @param startPoint 起点
     * @param endPoint   终点
     * @param Threshold  阈值/间距
     * @return 返回等距点位（包含起点终点）
     */
    public static List<Point2D.Double> BasePoint(Point2D.Double startPoint, Point2D.Double endPoint, int Threshold) {

        // 初始化结果集
        ArrayList<Point2D.Double> resultList = new ArrayList<>();

        // 两点之间距离
        BigDecimal distance = BigDecimal.valueOf(startPoint.distance(endPoint));

        if (0 > distance.compareTo(BigDecimal.valueOf(Threshold))) {
            resultList.add(startPoint);
            resultList.add(endPoint);
            return resultList;
        }

        // 均分份数
        int numberOfCopies = distance.divide(BigDecimal.valueOf(Threshold), RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP).intValue();

        Point2D.Double tempPoint = new Point2D.Double();
        if ((startPoint.getX() >= endPoint.getX() && startPoint.getY() >= endPoint.getY()) || (startPoint.getX() < endPoint.getX() && startPoint.getY() > endPoint.getY())) {
            tempPoint = startPoint;
            startPoint = endPoint;
            endPoint = tempPoint;
        }

        // 计算tan角度，防止重复计算
        double tan = slope(startPoint, endPoint);

        double xIncrement = BigDecimal.valueOf(tan2cos(tan)).multiply(BigDecimal.valueOf(Threshold)).doubleValue();
        double yIncrement = BigDecimal.valueOf(tan2sin(tan)).multiply(BigDecimal.valueOf(Threshold)).doubleValue();

        if (tan >= 0) {
            for (int i = 0; i < numberOfCopies; i++) {
                Point2D.Double onePoint = new Point2D.Double();
                onePoint.x = startPoint.getX() + BigDecimal.valueOf(xIncrement).multiply(BigDecimal.valueOf(i)).doubleValue();
                onePoint.y = startPoint.getY() + BigDecimal.valueOf(yIncrement).multiply(BigDecimal.valueOf(i)).doubleValue();
                resultList.add(onePoint);
            }
        } else {
            yIncrement = -yIncrement;
            for (int i = 0; i < numberOfCopies; i++) {
                Point2D.Double onePoint = new Point2D.Double();
                onePoint.x = startPoint.getX() - BigDecimal.valueOf(xIncrement).multiply(BigDecimal.valueOf(i)).doubleValue();
                onePoint.y = startPoint.getY() + BigDecimal.valueOf(yIncrement).multiply(BigDecimal.valueOf(i)).doubleValue();
                resultList.add(onePoint);
            }
        }

        resultList.add(endPoint);

        if (tempPoint.getX() != 0 && tempPoint.getY() != 0) {
            Collections.reverse(resultList);
        }

        return resultList;
    }

    /**
     * 计算两点之间距离
     *
     * @param startPoint 起点
     * @param endPoint   终点
     * @return 返回两点之间距离
     */
    public static Double distance(Point2D.Double startPoint, Point2D.Double endPoint) {
        return new BigDecimal(Math.sqrt(Math.pow(endPoint.getX() - startPoint.getX(), 2) + Math.pow(endPoint.getY() - startPoint.getY(), 2))).setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 计算两点之间斜率 tanα
     *
     * @param startPoint 起点
     * @param endPoint   终点
     * @return 返回两点之间斜率
     */
    public static Double slope(Point2D.Double startPoint, Point2D.Double endPoint) {
        // 两点水平
        if (startPoint.getY() == endPoint.getY()) {
            return 0D;
        }
        // 两点垂直
        else if (startPoint.getX() == endPoint.getX()) {
            return Double.POSITIVE_INFINITY;
        }
        // 两点非水平垂直关系
        else {
            return new BigDecimal(endPoint.getY() - startPoint.getY()).divide(new BigDecimal(endPoint.getX() - startPoint.getX()), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

    /**
     * 计算两点斜线方程
     *
     * @param startPoint 起点
     * @param endPoint   终点
     * @return 返回斜线方程
     */
    public static Map<String, Double> slashEquation(Point2D.Double startPoint, Point2D.Double endPoint) {
        Map<String, Double> slashEq = new LinkedHashMap<>();
        // A = y2 - y1
        double A = endPoint.getY() - startPoint.getY();
        // B = x1 - x2
        double B = startPoint.getX() - endPoint.getX();
        // C = x2 * y1 -x1 * y2
        double C = endPoint.getX() * startPoint.getY() - startPoint.getX() * endPoint.getY();
        slashEq.put("A", A);
        slashEq.put("B", B);
        slashEq.put("C", C);
        // 斜线方程为： Ax + By + C = 0
        return slashEq;
    }

    /**
     * 计算目标点在线段上的映射点
     *
     * @param startPoint 起点
     * @param endPoint   终点
     * @param realPoint  目标点
     * @return 返回目标点在指定线段上的映射点
     */
    public static Point2D.Double mapPoint(Point2D.Double startPoint, Point2D.Double endPoint, Point2D.Double realPoint) {

        // 求单位向量
        Point2D.Double unitVector = new Point2D.Double(endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());
        BigDecimal xVector = BigDecimal.valueOf(unitVector.getX()).divide(BigDecimal.valueOf(Math.sqrt(Math.pow(unitVector.getX(), 2) + Math.pow(unitVector.getY(), 2))), 15, RoundingMode.HALF_UP);
        BigDecimal yVector = BigDecimal.valueOf(unitVector.getY()).divide(BigDecimal.valueOf(Math.sqrt(Math.pow(unitVector.getX(), 2) + Math.pow(unitVector.getY(), 2))), 15, RoundingMode.HALF_UP);

        // 目标向量
        double xRealVector = realPoint.getX() - startPoint.getX();
        double yRealVector = realPoint.getY() - startPoint.getY();

        // v*u(T)*u
        double x = new BigDecimal(xRealVector)
                .multiply(BigDecimal.valueOf(xVector.doubleValue()).pow(2))
                .add(new BigDecimal(yRealVector)
                        .multiply(BigDecimal.valueOf(xVector.doubleValue())
                                .multiply(BigDecimal.valueOf(yVector.doubleValue()))))
                .setScale(15, RoundingMode.HALF_UP)
                .add(BigDecimal.valueOf(startPoint.getX())).doubleValue();
        double y = new BigDecimal(xRealVector)
                .multiply(BigDecimal.valueOf(xVector.doubleValue()))
                .multiply(BigDecimal.valueOf(yVector.doubleValue()))
                .add(new BigDecimal(yRealVector)
                        .multiply(BigDecimal.valueOf(yVector.doubleValue()).pow(2)))
                .setScale(15, RoundingMode.HALF_UP)
                .add(BigDecimal.valueOf(startPoint.getY())).doubleValue();

        return new Point2D.Double(x, y);

    }

    /**
     * 计算是否点是否在线上
     *
     * @param startPoint 起点
     * @param endPoint   终点
     * @param realPoint  监测点
     * @return
     */
    public static Boolean pointLineRelationship(Point2D.Double startPoint, Point2D.Double endPoint, Point2D.Double realPoint) {

        Boolean flag = false;

        // StartEnd 向量
        Point2D.Double v1 = new Point2D.Double(endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());
        // RealStart 向量
        Point2D.Double v2 = new Point2D.Double(realPoint.getX() - startPoint.getX(), realPoint.getY() - startPoint.getY());

        //todo 向量叉乘得0 说明共线 ， 点积>0 说明同向 点积<0 说明反向
        // v1 X v2
        double crossProduct = new BigDecimal(Double.toString((v1.getX() * v2.getY() - v1.getY() * v2.getX()))).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (crossProduct == 0) {
            double dotProduct = new BigDecimal(v1.getX()).multiply(new BigDecimal(v2.getX())).add(new BigDecimal(v1.getY()).multiply(new BigDecimal(v2.getY()))).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
            if (dotProduct > 0) {
                flag = true;
            }
        }

        return flag;

    }

    public static Double tan2cos(Double tan) {
        return Math.sqrt(1 / (1 + Math.pow(tan, 2)));
    }

    public static Double tan2sin(Double tan) {
        double cos = Math.sqrt(1 / (1 + Math.pow(tan, 2)));
        double sin = Math.sqrt(1 - Math.pow(cos, 2));
        return tan > 0 ? sin : -sin;
    }

    /**
     * 寻找最佳匹配监控点
     *
     * @param point            监测点位
     * @param monitorPointList 监测点
     * @param Threshold
     * @return 返回指定基础点位中的命中目标
     * 时间复杂度 O(mn)
     */
    public static Point2D.Double findMonitorPoint(Point2D.Double point, List<Point2D.Double> monitorPointList, int Threshold) {
        List<Point2D.Double> collect = monitorPointList.stream()
                .filter(onePoint -> onePoint.getX() > (point.getX() - Threshold) && onePoint.getX() < (point.getX() + Threshold) && onePoint.getY() > (point.getY() - Threshold) && onePoint.getY() < (point.getY() + Threshold))
                .collect(Collectors.toList());
        double shortestDistance = Threshold;
        Point2D.Double result = new Point2D.Double();
        for (Point2D.Double onePoint : collect) {
            if (onePoint.distance(point) < shortestDistance) {
                result = onePoint;
            }
            shortestDistance = onePoint.distance(point);
        }
        return result;
    }

    /**
     * 寻找最佳匹配监控点
     *
     * @param point            监测点位
     * @param monitorPointList 监测点
     * @param Threshold
     * @return 返回指定基础点位中的命中目标
     * 时间复杂度 O(mn)
     */
    public static Point2D.Double findMonitorPointPro(Point2D.Double point, List<Point2D.Double> monitorPointList, int Threshold) {
        List<Point2D.Double> collect = monitorPointList.stream()
                .filter(onePoint -> onePoint.getX() > (point.getX() - Threshold) && onePoint.getX() < (point.getX() + Threshold) && onePoint.getY() > (point.getY() - Threshold) && onePoint.getY() < (point.getY() + Threshold))
                .collect(Collectors.toList());
        double shortestDistance = Threshold;
        Point2D.Double result = new Point2D.Double();
        for (Point2D.Double onePoint : collect) {
            if (onePoint.distance(point) < shortestDistance) {
                result = onePoint;
            }
            shortestDistance = onePoint.distance(point);
        }
        return result;
    }

    /**
     * 根据目标点的x轴坐标进行二分查找
     *
     * @param point            监测点位
     * @param monitorPointList 监测点
     * @param low
     * @param high
     * @param thresold
     * @return 返回指定基础点位中的命中目标
     * 问题：当thresold精度高的时候，命中目标失效；当精度低时命中速度极快
     * 时间复杂度 O(log(mn))
     */
    public static Point2D.Double findMonitorPointByBinarySearch(Point2D.Double point, List<Point2D.Double> monitorPointList, int low, int high, int thresold) {
        double targetX = point.getX();
        double targetY = point.getY();

        int x_low, y_low;
        x_low = y_low = low;

        int x_high, y_high;
        x_high = y_high = high;

        double x_result, y_result;
        x_result = y_result = -1;

        //todo --------------------------------对x轴进行二分查找-------------------------------------------
        while (x_low < x_high) {
            if (monitorPointList.get(low).getX() > monitorPointList.get(high).getX()) {
                Collections.reverse(monitorPointList);
            }
            int xmiddle = (x_low + x_high) / 2;
            if (monitorPointList.get(xmiddle).getX() < targetX) {
                x_low = xmiddle + 1;
            } else if (monitorPointList.get(xmiddle).getX() > targetX) {
                x_high = xmiddle - 1;
            } else {
                x_result = monitorPointList.get(xmiddle).getX();
                break;
            }
        }

        if (x_low >= x_high) {
            double shortestDistance = thresold;
            int x_leftRange = x_low - 2 < 0 ? 0 : x_low - 2;
            int x_rightRange = x_high + 2 > high ? x_high : x_high + 2;
            for (int i = x_leftRange; i <= x_rightRange; i++) {
                double s = monitorPointList.get(i).distance(point);
                if (monitorPointList.get(i).distance(point) < shortestDistance) {
                    x_result = monitorPointList.get(i).getX();
                    shortestDistance = monitorPointList.get(i).distance(point);
                }
            }
        }

        //todo --------------------------------对y轴进行二分查找-------------------------------------------
        while (y_low < y_high) {
            if (monitorPointList.get(low).getY() > monitorPointList.get(high).getY()) {
                Collections.reverse(monitorPointList);
            }
            int ymiddle = (y_low + y_high) / 2;
            if (monitorPointList.get(ymiddle).getY() < targetY) {
                y_low = ymiddle + 1;
            } else if (monitorPointList.get(ymiddle).getY() > targetY) {
                y_high = ymiddle - 1;
            } else {
                y_result = monitorPointList.get(ymiddle).getY();
                break;
            }
        }

        if (y_low >= y_high) {
            double shortestDistance = thresold;
            int y_downRange = y_low - 1 < 0 ? 0 : y_low - 1;
            int y_upRange = y_high + 1 > high ? y_high : y_high + 1;
            for (int i = y_downRange; i <= y_upRange; i++) {
                if (monitorPointList.get(i).distance(point) < shortestDistance) {
                    y_result = monitorPointList.get(i).getY();
                    shortestDistance = monitorPointList.get(i).distance(point);
                }
            }
        }

        if (x_result == -1 || y_result == -1) {
            return new Point2D.Double(0, 0);
        } else {
            return new Point2D.Double(x_result, y_result);
        }
    }

    /**
     * 判断是否在以起点和终点构成的圆内
     *
     * @param point      目标点
     * @param startPoint 起点
     * @param endPoint   终点
     * @return
     */
    public static Boolean isInsideCircle(Point2D.Double point, Point2D.Double startPoint, Point2D.Double endPoint) {

        double circle_x = new BigDecimal(endPoint.getX()).add(BigDecimal.valueOf(startPoint.getX())).divide(BigDecimal.valueOf(2)).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
        double circle_y = new BigDecimal(endPoint.getY()).add(BigDecimal.valueOf(startPoint.getY())).divide(BigDecimal.valueOf(2)).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();

        Point2D.Double circle = new Point2D.Double(circle_x, circle_y);
        double circle2point = circle.distance(point);
        double radius = startPoint.distance(endPoint) / 2;
        if (circle.distance(point) <= startPoint.distance(endPoint) / 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 创建依据起点终点画出指定区域
     *
     * @param startPoint 起点
     * @param endPoint   终点
     * @param width      区域宽度
     * @return 返回以起点为首 4个区域集合
     */
    public static List<Point2D.Double> createRegion(Point2D.Double startPoint, Point2D.Double endPoint, Double width) {

        Double tanα = slope(startPoint, endPoint);
        Double cosα = tan2cos(tanα);
        Double sinα = tan2sin(tanα);

        Double line_1 = width / 2;
        Double line_2 = line_1 * cosα;
        Double line_3 = line_1 * sinα;

        List<Point2D.Double> list = new ArrayList<>();
        list.add(new Point2D.Double(startPoint.getX() + line_3, startPoint.getY() - line_2));
        list.add(new Point2D.Double(startPoint.getX() - line_3, startPoint.getY() + line_2));
        list.add(new Point2D.Double(endPoint.getX() - line_3, endPoint.getY() + line_2));
        list.add(new Point2D.Double(endPoint.getX() + line_3, endPoint.getY() - line_2));


        return list;
    }

    public static Point2D.Double getIntermediatePoint(Point2D.Double startPoint, Point2D.Double endPoint) {
        return new Point2D.Double((startPoint.getX() + endPoint.getX()) / 2, (startPoint.getY() + endPoint.getY()) / 2);
    }


    /**
     * 判断点是否在多边形内
     *
     * @param point 检测点
     * @param pts   多边形的顶点
     * @return 点在多边形内返回true, 否则返回false
     */
    public static boolean IsPtInPoly(Point2D.Double point, List<Point2D.Double> pts) {

        int N = pts.size();
        boolean boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
        int intersectCount = 0;//cross points count of x
        double precision = 2e-10; //浮点类型计算时候与0比较时候的容差
        Point2D.Double p1, p2;//neighbour bound vertices
        Point2D.Double p = point; //当前点

        p1 = pts.get(0);//left vertex
        for (int i = 1; i <= N; ++i) {//check all rays
            if (p.equals(p1)) {
                return boundOrVertex;//p is an vertex
            }

            p2 = pts.get(i % N);//right vertex
            if (p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)) {//ray is outside of our interests
                p1 = p2;
                continue;//next ray left point
            }

            if (p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)) {//ray is crossing over by the algorithm (common part of)
                if (p.y <= Math.max(p1.y, p2.y)) {//x is before of ray
                    if (p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)) {//overlies on a horizontal ray
                        return boundOrVertex;
                    }

                    if (p1.y == p2.y) {//ray is vertical
                        if (p1.y == p.y) {//overlies on a vertical ray
                            return boundOrVertex;
                        } else {//before ray
                            ++intersectCount;
                        }
                    } else {//cross point on the left side
                        double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;//cross point of y
                        if (Math.abs(p.y - xinters) < precision) {//overlies on a ray
                            return boundOrVertex;
                        }

                        if (p.y < xinters) {//before ray
                            ++intersectCount;
                        }
                    }
                }
            } else {//special case when ray is crossing through the vertex
                if (p.x == p2.x && p.y <= p2.y) {//p crossing over p2
                    Point2D.Double p3 = pts.get((i + 1) % N); //next vertex
                    if (p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)) {//p.x lies between p1.x & p3.x
                        ++intersectCount;
                    } else {
                        intersectCount += 2;
                    }
                }
            }
            p1 = p2;//next ray left point
        }

        if (intersectCount % 2 == 0) {//偶数在多边形外
            return false;
        } else { //奇数在多边形内
            return true;
        }

    }


    public static List<Long> AutoPathFinding(LinkedList<Long> linkedList[], Long start, Long end) {
        LinkedList<Long> path = new LinkedList<>();
        if (start.equals(end)) {
            path.add(start);
            return path;
        }

        Queue<Long> queue = new LinkedList<>();
        queue.add(start);

        boolean isFind[] = new boolean[linkedList.length];
        isFind[start.intValue()] = true;

        Long[] prev = new Long[linkedList.length];
        for (int i = 0; i < prev.length; i++) {
            prev[i] = -1L;
        }

        while (!queue.isEmpty()) {
            Long current = queue.poll();
            for (int i = 0; i < linkedList[current.intValue()].size(); i++) {
                Long target = linkedList[current.intValue()].get(i);

                if (!isFind[target.intValue()]) {
                    isFind[target.intValue()] = true;
                    prev[target.intValue()] = current;

                    if (target.equals(end)) {
                        showSearchPath(path, prev, start, end);
                        queue.clear();
                        break;
                    } else {
                        queue.add(target);
                    }
                }
            }
        }
        return path;
    }

    private static void showSearchPath(LinkedList<Long> path, Long[] prev, Long start, Long end) {
        if (prev[end.intValue()] != -1 && !start.equals(end)) {
            showSearchPath(path, prev, start, prev[end.intValue()]);
        }
        path.add(end);
    }

    public static Point2D.Double avgPoint(List<Point2D.Double> pointList) {
        double sumX = 0;
        double sumY = 0;
        int size = pointList.size();
        for (Point2D.Double aPoint : pointList) {
            sumX += aPoint.getX();
            sumY += aPoint.getY();
        }
        return new Point2D.Double(sumX / size, sumY / size);
    }
}

