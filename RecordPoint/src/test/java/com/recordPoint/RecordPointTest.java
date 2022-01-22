package com.recordPoint;

import com.recordPoint.utils.PointUtils;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.context.Theme;

import java.awt.*;
import java.awt.geom.Point2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

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

        Point2D.Double real = new Point2D.Double(6659.917802107004, 5401.0134924270615);
        Point2D.Double aDouble = PointUtils.mapPoint(startPoint, endPoint, real);
        aDouble.getY();

    }

    //todo 记录原始点位至缓存当中，定期处理/队列处理？ 归正算法
    @Test
    public void test2() {

        List<Route> routeList = new ArrayList<>();
        Route route1 = new Route();
        route1.startPoint.setLocation(7780.687624317111, 6276.435397422331);
        route1.endPoint.setLocation(6579.173021079166, 5371.787018467159);
        routeList.add(route1);

        Route route2 = new Route();
        route2.startPoint.setLocation(6579.057884500161, 5371.787018467159);
        route2.endPoint.setLocation(6430.653583819783, 5370.895430693774);
        routeList.add(route2);

        Point2D.Double real = new Point2D.Double(6659.917802107004, 5401.0134924270615);

        routeList.forEach(route -> {
            Point2D.Double mapPoint = PointUtils.mapPoint(route.getStartPoint(), route.getEndPoint(), real);
            Boolean aBoolean = PointUtils.pointLineRelationship(route.getStartPoint(), route.getEndPoint(), mapPoint);
            mapPoint.toString();
        });

    }

    @Test
    public void test() {
        Point2D.Double startPoint = new Point2D.Double(0, 0);
        Point2D.Double endPoint = new Point2D.Double(1, 2);
        Point2D.Double real = new Point2D.Double(3, 4);
        Point2D.Double mapPoint = PointUtils.mapPoint(startPoint, endPoint, real);
        Boolean aBoolean = PointUtils.pointLineRelationship(startPoint, endPoint, mapPoint);
        System.out.println(aBoolean);
    }


    @Data
    private class Route {

        private Point2D.Double startPoint = new Point2D.Double();
        private Point2D.Double endPoint = new Point2D.Double();

    }

    @Data
    class TestIm implements Callable<Integer> {

        private List<Integer> num;

        public TestIm(List<Integer> num) {
            this.num = num;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName() + "****进入callable了！");
            int count = 0;
            for (int i = 0; i < this.num.size(); i++) {
                count += this.num.get(i);
            }
            Thread.sleep(2000);
            return count;
        }
    }

    @Test
    public void test3() throws ExecutionException, InterruptedException {
        List<Integer> s1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            s1.add(i);
        }
        List<Integer> s2 = new ArrayList<>();
        for (int i = 76; i < 100; i++) {
            s2.add(i);
        }

        List<List<Integer>> dd = new ArrayList<>();
        dd.add(s1);
        dd.add(s2);

        for (int i = 0; i < 2; i++) {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'").format(System.currentTimeMillis()));
            FutureTask<Integer> ft = new FutureTask<>(new TestIm(dd.get(i)));
            Thread thread = new Thread(ft);
            thread.start();
            System.out.println(ft.get());
            System.out.println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'").format(System.currentTimeMillis()));
        }

    }
}
