package com.recordPoint;

import com.recordPoint.Service.RouteMonitoringPointsServiceImpl;
import com.recordPoint.Service.RouteServiceImpl;
import com.recordPoint.domain.Route;
import com.recordPoint.domain.RouteMonitoringPoints;
import com.recordPoint.redis.service.RedisService;
import com.recordPoint.utils.PointUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;
import org.springframework.ui.context.Theme;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

/**
 * @author chenming
 * @description 测试类
 * @create: 2022-01-21
 */
@SpringBootTest
@Slf4j
public class RecordPointTest {

    private int spacing = 1;
    private int Thresold = 12;

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

    @Test
    public void test4() {
        Point2D.Double startPoint = new Point2D.Double(-1, 0);
        Point2D.Double endPoint = new Point2D.Double(6, 0);
        List<Point2D.Double> doubles = PointUtils.BasePoint(startPoint, endPoint, Thresold);
        doubles.size();
    }

    @Autowired
    private RouteServiceImpl routeService;
    @Autowired
    private RouteMonitoringPointsServiceImpl routeMonitoringPointsService;

    @Test
    public void test5() {
        Point2D.Double startPoint = new Point2D.Double(2328.6945572151, 5652.370054894594);
        Point2D.Double endPoint = new Point2D.Double(2328.6945572151, 4497.426799323973);
        List<Point2D.Double> monitoringPoints = PointUtils.BasePoint(startPoint, endPoint, Thresold);
        Route route = new Route();
        route.setIdentifies(1);
        route.setStartX(startPoint.getX());
        route.setStartX(startPoint.getY());
        route.setEndX(endPoint.getX());
        route.setEndY(endPoint.getY());

        List<RouteMonitoringPoints> list = new ArrayList<>();
        monitoringPoints.forEach(point -> {
            RouteMonitoringPoints routeMonitoringPoints = new RouteMonitoringPoints();
            routeMonitoringPoints.setIdentifies(route.getIdentifies());
            routeMonitoringPoints.setPointX(point.getX());
            routeMonitoringPoints.setPointY(point.getY());
            list.add(routeMonitoringPoints);
        });

        routeService.save(route);
        routeMonitoringPointsService.saveBatch(list);

    }

    @Autowired
    private RedisService redisService;

    @Test
    public void test6() {
        redisService.setCacheObject("#MonitorPoint-1", routeMonitoringPointsService.list());
    }

    @Test
    @RepeatedTest(10)
    public void test7() {
        log.info("--------------------------------------------------------------------------------------");
        // 监测点
        List<Point2D.Double> pointList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            pointList.add(new Point2D.Double(2332.327865107768, 5657.278417943055 - 0.1 * i));
        }

        Point2D.Double startPoint = new Point2D.Double(2332.327865107768, 5657.290505716259);
        Point2D.Double endPoint = new Point2D.Double(2332.667641813025, 4474.353966948051);
        long start = System.currentTimeMillis();
        List<Point2D.Double> monitoringPoints = PointUtils.BasePoint(startPoint, endPoint, Thresold);
        log.info("拆分监测点耗时：{}ms", System.currentTimeMillis() - start);

        List<Point2D.Double> result = new ArrayList<>();
        long Mostart = System.currentTimeMillis();
        pointList.forEach(point -> {
            Point2D.Double monitorPoint = PointUtils.findMonitorPoint(point, monitoringPoints, Thresold);
            result.add(monitorPoint);
        });
        log.info("查找列表监测点耗时：{}ms", System.currentTimeMillis() - Mostart);

        List<Point2D.Double> BinarySearchresult = new ArrayList<>();
        long BinarySearch = System.currentTimeMillis();
        pointList.forEach(point -> {
            Point2D.Double monitorPoint = PointUtils.findMonitorPointByBinarySearch(point, monitoringPoints, 0, monitoringPoints.size() - 1, Thresold);
            BinarySearchresult.add(monitorPoint);
        });
        log.info("查找二分监测点耗时：{}ms", System.currentTimeMillis() - BinarySearch);

        for (int i = 0; i < 50; i++) {
            log.info("-------分割线-------");
            log.info("原始点位：{}  列表监测点：{}  二分监测点；{}", pointList.get(i).toString(), result.get(i).toString(), BinarySearchresult.get(i).toString());
            log.info("原始点位距离列表检测点：{}，原始点位距离二分监测点：{}", result.get(i).distance(pointList.get(i)), BinarySearchresult.get(i).distance(pointList.get(i)));
        }
    }

    @Test
    public void test8() {
        Point2D.Double point = new Point2D.Double(2333.0645772610064, 5318.034991439028);
        Point2D.Double aDouble = new Point2D.Double(2328.6945572151, 5313.426799323973);
        double distance = point.distance(aDouble);
        System.out.printf(String.valueOf(distance));
    }

    @Test
    public void test10() {
        Point2D.Double startPoint = new Point2D.Double(2332.327865107768, 5657.290505716259);
        Point2D.Double endPoint = new Point2D.Double(2332.667641813025, 4474.353966948051);

    }

    @Test
    public void test11() {
        Point2D.Double startPoint = new Point2D.Double(1935.3574324372453, 4472.119319823549);
        Point2D.Double endPoint = new Point2D.Double(2417.273803792798, 4471.462758009713);
        List<Point2D.Double> doubles = PointUtils.BasePoint(startPoint, endPoint, Thresold);
        doubles.size();
    }

    @Test
    public void test12() {
        Point2D.Double startPoint = new Point2D.Double(0, 0);
        Point2D.Double endPoint = new Point2D.Double(5, 0);
        List<Point2D.Double> doubles = PointUtils.BasePoint(startPoint, endPoint, 1);
        Point2D.Double targetPoint = new Point2D.Double(3, 0);
        Point2D.Double monitorPointByBinarySearch = PointUtils.findMonitorPointByBinarySearch(targetPoint, doubles, 0, doubles.size() - 1, 1);
        doubles.size();
    }

    @Test
    public void test13() {
        Point2D.Double startPoint = new Point2D.Double(5, 5);
        Point2D.Double endPoint = new Point2D.Double(0, 0);

        Point2D.Double targetPoint = new Point2D.Double(5, 4.8);
        List<Point2D.Double> doubles = PointUtils.BasePoint(startPoint, endPoint, 1);
        log.info(PointUtils.findMonitorPointByBinarySearch(targetPoint, doubles, 0, doubles.size() - 1, 1).toString());
    }

}
