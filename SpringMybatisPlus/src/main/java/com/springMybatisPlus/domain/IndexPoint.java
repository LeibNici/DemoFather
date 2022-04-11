package com.springMybatisPlus.domain;

import java.awt.geom.Point2D;

/**
 * @author chenming
 * @description
 * @create: 2022-03-17
 */
public class IndexPoint {

    private int id;
    private Point2D.Double point;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Point2D.Double getPoint() {
        return point;
    }

    public void setPoint(Point2D.Double point) {
        this.point = point;
    }

}
