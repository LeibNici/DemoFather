package com.cm.PostGre.domain;

import lombok.Data;

/**
 * @author chenming
 * @description
 * @create: 2022-04-18
 */
@Data
public class BusRegionGeoJsonDO {
    private Long id;
    private String regionName;
    private String geoJson;
}
