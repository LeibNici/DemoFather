package com.recordPoint.utils;

import com.alibaba.druid.support.spring.stat.annotation.Stat;
import sun.util.resources.cldr.ig.CurrencyNames_ig;

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
        BigDecimal xVector = new BigDecimal(unitVector.getX()).divide(new BigDecimal(Math.sqrt(Math.pow(unitVector.getX(), 2) + Math.pow(unitVector.getY(), 2))), 4, BigDecimal.ROUND_HALF_UP);
        BigDecimal yVector = new BigDecimal(unitVector.getY()).divide(new BigDecimal(Math.sqrt(Math.pow(unitVector.getX(), 2) + Math.pow(unitVector.getY(), 2))), 4, BigDecimal.ROUND_HALF_UP);

        // 目标向量
        double xRealVector = realPoint.getX() - startPoint.getX();
        double yRealVector = realPoint.getY() - startPoint.getY();

        double x = new BigDecimal(xRealVector)
                .multiply(new BigDecimal(xVector.doubleValue()).pow(2))
                .add(new BigDecimal(yRealVector)
                        .multiply(new BigDecimal(xVector.doubleValue())
                                .multiply(new BigDecimal(yVector.doubleValue()))))
                .setScale(5, BigDecimal.ROUND_HALF_UP).add(new BigDecimal(startPoint.getX())).doubleValue();
        double y = new BigDecimal(xRealVector)
                .multiply(new BigDecimal(xVector.doubleValue()))
                .multiply(new BigDecimal(yVector.doubleValue()))
                .add(new BigDecimal(yRealVector)
                        .multiply(new BigDecimal(yVector.doubleValue()).pow(2)))
                .setScale(5, BigDecimal.ROUND_HALF_UP)
                .add(new BigDecimal(startPoint.getY())).doubleValue();

        return new Point2D.Double(x, y);

    }


}
