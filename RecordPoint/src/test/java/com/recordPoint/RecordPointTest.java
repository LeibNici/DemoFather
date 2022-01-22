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

        Point2D.Double endPoint = new Point2D.Double(7783.036183263381, 6278.442228690571);
        Point2D.Double startPoint = new Point2D.Double(6579.057884500161, 5371.787018467159);
        
        Point2D.Double real = new Point2D.Double(6659.917802107004,5401.0134924270615);
        Point2D.Double aDouble = PointUtils.mapPoint(startPoint, endPoint, real);
        aDouble.getY();

    }


}
