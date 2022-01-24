package com.SpringPoints.domain;

import lombok.Data;

import java.awt.geom.Point2D;
import java.io.Serializable;

@Data
public class Route implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int identifies;
    private Double startX;
    private Double startY;

    private Double endX;
    private Double endY;


}
