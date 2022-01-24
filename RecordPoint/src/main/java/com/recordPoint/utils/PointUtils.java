package com.recordPoint.utils;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import org.springframework.context.annotation.Bean;
import sun.util.resources.cldr.ig.CurrencyNames_ig;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.math.BigInteger;
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
        // 两点之间距离
        Double distance = startPoint.distance(endPoint);
        // 均分份数
        BigDecimal numberOfCopies = new BigDecimal(distance).divide(new BigDecimal(Threshold), BigDecimal.ROUND_HALF_UP);

        Point2D.Double tempPoint = null;
        if (startPoint.getX() >= endPoint.getX() && startPoint.getY() > endPoint.getY()) {
            tempPoint = startPoint;
            startPoint = endPoint;
            endPoint = tempPoint;
        }

        // 初始化结果集
        ArrayList<Point2D.Double> resultList = new ArrayList<>();
        // 计算tan角度，防止重复计算
        double tan = slope(startPoint, endPoint);
        for (int i = 0; i < numberOfCopies.intValue(); i++) {
            Point2D.Double onePoint = new Point2D.Double();
            onePoint.x = startPoint.getX() + new BigDecimal(tan2cos(tan)).multiply(BigDecimal.valueOf(i)).multiply(BigDecimal.valueOf(Threshold)).doubleValue();
            onePoint.y = startPoint.getY() + new BigDecimal(tan2sin(tan)).multiply(BigDecimal.valueOf(i)).multiply(BigDecimal.valueOf(Threshold)).doubleValue();
            resultList.add(onePoint);
        }
        resultList.add(endPoint);

        if (tempPoint!=null) {
            Collections.reverse(resultList);
        }

        return resultList;
    }

    /**
     * 计算两点之间距离
     *
     * @param startPoint 起点tantatttttttddd
     * @param endPoint   终点
     * @return 返回两点之间距离
     */
    public static Double distance(Point2D.Double startPoint, Point2D.Double endPoint) {
        return new BigDecimal(Math.sqrt(Math.pow(endPoint.getX() - startPoint.getX(), 2) + Math.pow(endPoint.getY() - startPoint.getY(), 2))).setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 计算两点之间斜率
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
    public static Double slashEquation(Point2D.Double startPoint, Point2D.Double endPoint) {
        // A = y2 - y1
        double A = endPoint.getY() - startPoint.getY();
        // B = x1 - x2
        double B = startPoint.getX() - endPoint.getX();
        // C = x2 * y1 -x1 * y2
        double C = endPoint.getX() * startPoint.getY() - startPoint.getX() * endPoint.getY();

        // 斜线方程为： Ax + By + C = 0
        return null;
    }

    public static Point2D.Double mapPoint(Point2D.Double startPoint, Point2D.Double endPoint, Point2D.Double realPoint) {

        // 求单位向量
        Point2D.Double unitVector = new Point2D.Double(endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());
        BigDecimal xVector = new BigDecimal(unitVector.getX()).divide(new BigDecimal(Math.sqrt(Math.pow(unitVector.getX(), 2) + Math.pow(unitVector.getY(), 2))), 15, BigDecimal.ROUND_HALF_UP);
        BigDecimal yVector = new BigDecimal(unitVector.getY()).divide(new BigDecimal(Math.sqrt(Math.pow(unitVector.getX(), 2) + Math.pow(unitVector.getY(), 2))), 15, BigDecimal.ROUND_HALF_UP);

        // 目标向量
        double xRealVector = realPoint.getX() - startPoint.getX();
        double yRealVector = realPoint.getY() - startPoint.getY();

        double x = new BigDecimal(xRealVector)
                .multiply(new BigDecimal(xVector.doubleValue()).pow(2))
                .add(new BigDecimal(yRealVector)
                        .multiply(new BigDecimal(xVector.doubleValue())
                                .multiply(new BigDecimal(yVector.doubleValue()))))
                .setScale(15, BigDecimal.ROUND_HALF_UP).add(new BigDecimal(startPoint.getX())).doubleValue();
        double y = new BigDecimal(xRealVector)
                .multiply(new BigDecimal(xVector.doubleValue()))
                .multiply(new BigDecimal(yVector.doubleValue()))
                .add(new BigDecimal(yRealVector)
                        .multiply(new BigDecimal(yVector.doubleValue()).pow(2)))
                .setScale(15, BigDecimal.ROUND_HALF_UP)
                .add(new BigDecimal(startPoint.getY())).doubleValue();

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

    public static Point2D.Double findMonitorPoint(Point2D.Double point, List<Point2D.Double> monitorPointList, int low, int high, int Threshold) {

        int middle = (low + high) / 2;

        Point2D.Double target = monitorPointList.get(middle);
        if (point.distance(target) > Threshold) {
            if (point.getX() >= target.getX() && point.getY() >= target.getY()) {
                findMonitorPoint(point, monitorPointList, middle + 1, high, Threshold);
            } else {
                findMonitorPoint(point, monitorPointList, low, middle - 1, Threshold);
            }
        } else {
            return target;
        }
        return null;
    }

    /**
     * 使用递归的二分查找
     *
     * @param arr 有序数组
     * @param key 待查找关键字
     * @return 找到的位置
     */
    public static int recursionBinarySearch(int[] arr, int key, int low, int high) {

        if (key < arr[low] || key > arr[high] || low > high) {
            return -1;
        }

        int middle = (low + high) / 2;            //初始中间位置
        if (arr[middle] > key) {
            //比关键字大则关键字在左区域
            return recursionBinarySearch(arr, key, low, middle - 1);
        } else if (arr[middle] < key) {
            //比关键字小则关键字在右区域
            return recursionBinarySearch(arr, key, middle + 1, high);
        } else {
            return middle;
        }

    }

}

