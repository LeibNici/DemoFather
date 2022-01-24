package com.recordPoint.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class RouteMonitoringPoints implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int identifies;
    private Double pointX;
    private Double pointY;

}
