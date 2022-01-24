package com.springpoints;

import com.SpringPoints.Service.RouteService;
import com.SpringPoints.Service.RouteServiceImpl;
import com.SpringPoints.domain.Route;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.geom.Point2D;

/**
 * @author chenming
 * @description
 * @create: 2022-01-24
 */
@SpringBootTest
public class pointTest {

    @Autowired
    private RouteServiceImpl routeService;

    @Test
    public void TR(){
        Route route = new Route();
        Point2D.Double startPoint = new Point2D.Double(2328.6945572151, 5652.370054894594);
        Point2D.Double endPoint = new Point2D.Double(2328.6945572151, 5652.370054894594);

        route.setStartX(startPoint.getX());
        route.setStartY(startPoint.getY());
        route.setEndX(endPoint.getX());
        route.setEndY(endPoint.getY());

        routeService.save(route);
    }

}
