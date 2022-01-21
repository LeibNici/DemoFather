package com.recordPoint;

import com.recordPoint.utils.PointUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * @author chenming
 * @description 测试类
 * @create: 2022-01-21
 */
@SpringBootTest
public class RecordPointTest {

    private int spacing = 1;
    private int Thresold = 1;

    /**
     * 点位回归
     */
    @Test
    public void test1() {

        Point2D.Double startPoint = new Point2D.Double(0, 0);
        Point2D.Double endPoint = new Point2D.Double(5, 0);
        Double slope = PointUtils.slope(startPoint, endPoint);
        List<Point2D.Double> pointList = PointUtils.BasePoint(startPoint, endPoint, Thresold);
        pointList.size();

    }


}
