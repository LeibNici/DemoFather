package com.recordPoint.utils;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
        Double distance = distance(startPoint, endPoint);
        // 均分份数
        BigDecimal numberOfCopies = new BigDecimal(distance).divide(new BigDecimal(Threshold));

        // 初始化结果集
        List<Point2D.Double> resultList = new LinkedList<>();
        for (int i = 0; i < numberOfCopies.intValue(); i++) {
            Point2D.Double onePoint = new Point2D.Double(startPoint.getX() + Threshold * i, startPoint.getY() + Threshold * i);
            resultList.add(onePoint);
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
        return Math.sqrt(Math.pow(endPoint.getX() - startPoint.getX(), 2) + Math.pow(endPoint.getY() - startPoint.getY(), 2));
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
            return 1D;
        }
        // 两点非水平垂直关系
        else {
            return new BigDecimal(endPoint.getY() - startPoint.getY()).divide(new BigDecimal(endPoint.getX() - startPoint.getX())).doubleValue();
        }
    }

}
