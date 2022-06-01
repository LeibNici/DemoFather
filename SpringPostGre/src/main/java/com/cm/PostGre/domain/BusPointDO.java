package com.cm.PostGre.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chenming
 * @description
 * @create: 2022-04-16
 */
@Data
public class BusPointDO {
    private BigDecimal pointX;
    private BigDecimal pointY;
}
