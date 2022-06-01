package com.springMybatisPlus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.springMybatisPlus.domain.BusRegionSub;
import com.springMybatisPlus.domain.BusRouteMonitoringPoints;
import com.springMybatisPlus.domain.BusVehicleNetworkPositionRecord;
import com.springMybatisPlus.domain.IndexPoint;
import com.springMybatisPlus.mapper.BusRegionSubMapper;
import com.springMybatisPlus.service.BusVehicleNetworkPositionRecordService;
import com.springMybatisPlus.service.IBusRouteMonitoringPointsService;
import com.springMybatisPlus.utils.PointUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Primary;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@Slf4j
public class mainTest {

    @Autowired
    private BusRegionSubMapper busRegionSubMapper;

    @Autowired
    private BusVehicleNetworkPositionRecordService busVehicleNetworkPositionRecordService;

    @Autowired
    private IBusRouteMonitoringPointsService busRouteMonitoringPointsService;

    @Test
    void test1(){
        QueryWrapper<BusRegionSub> regionQ = new QueryWrapper();
        regionQ.orderByAsc("sub_id");
        List<BusRegionSub> list = busRegionSubMapper.selectList(regionQ);

        Map<Integer, List<BusRegionSub>> covMap = list.stream().collect(Collectors.groupingBy(BusRegionSub::getRegionId));

        Map<Integer, List<Point2D.Double>> result = new HashMap<>();

        for (Integer key : covMap.keySet()) {
            List<BusRegionSub> busRegionSubs = covMap.get(key);
            List<Point2D.Double> pointList = new ArrayList<>();
            busRegionSubs.forEach(busRegionSub -> pointList.add(new Point2D.Double(busRegionSub.getSubRegionX().doubleValue(), busRegionSub.getSubRegionY().doubleValue())));
            result.put(key, pointList);
        }

        log.info("cov");

    }

    @Test
    void test2(){

        QueryWrapper<BusVehicleNetworkPositionRecord> queryWrapper = new QueryWrapper();
        queryWrapper.select("car_location_num").groupBy("car_location_num");
        List<BusVehicleNetworkPositionRecord> list1 = busVehicleNetworkPositionRecordService.list(queryWrapper);

        Map<Integer,List<BusVehicleNetworkPositionRecord>> map = new HashMap<>();

        for (BusVehicleNetworkPositionRecord record : list1) {
            QueryWrapper<BusVehicleNetworkPositionRecord> tmp = new QueryWrapper();
            tmp.eq("car_location_num",record.getCarLocationNum());
            List<BusVehicleNetworkPositionRecord> tmpList = busVehicleNetworkPositionRecordService.list(tmp);
            map.put(record.getCarLocationNum(),tmpList);
        }

        log.info("record");

    }

    @Test
    void test3(){
        List<BusRouteMonitoringPoints> list = busRouteMonitoringPointsService.list();

        Map<Integer, List<BusRouteMonitoringPoints>> mapM = list.stream().collect(Collectors.groupingBy(BusRouteMonitoringPoints::getRegionId));

        Map<Integer,List<IndexPoint>> monitorMap = new HashMap<>();

        for (Integer key : mapM.keySet()) {
            List<IndexPoint> list1 = new ArrayList<>();
            mapM.get(key).forEach(point->{

                IndexPoint indexPoint = new IndexPoint();
                indexPoint.setId(point.getIdentifies());
                indexPoint.setPoint(new Point2D.Double(point.getPointX().doubleValue(),point.getPointY().doubleValue()));

                list1.add(indexPoint);

            });
            monitorMap.put(key,list1);
        }

        log.info("monitor");


    }

    @Test
    void test4(){

        QueryWrapper<BusRegionSub> regionQ = new QueryWrapper();
        regionQ.orderByAsc("sub_id");
        List<BusRegionSub> regionList = busRegionSubMapper.selectList(regionQ);

        Map<Integer, List<BusRegionSub>> covMap = regionList.stream().collect(Collectors.groupingBy(BusRegionSub::getRegionId));

        Map<Integer, List<Point2D.Double>> regionMap = new HashMap<>();

        for (Integer key : covMap.keySet()) {
            List<BusRegionSub> busRegionSubs = covMap.get(key);
            List<Point2D.Double> pointList = new ArrayList<>();
            busRegionSubs.forEach(busRegionSub -> pointList.add(new Point2D.Double(busRegionSub.getSubRegionX().doubleValue(), busRegionSub.getSubRegionY().doubleValue())));
            regionMap.put(key, pointList);
        }

        log.info("cov--end");

//        -------------------------------------------------------------------------------------------------------------------------------------------

        List<BusRouteMonitoringPoints> list = busRouteMonitoringPointsService.list();

        Map<Integer, List<BusRouteMonitoringPoints>> mapM = list.stream().collect(Collectors.groupingBy(BusRouteMonitoringPoints::getRegionId));

        Map<Integer,List<IndexPoint>> monitorMap = new HashMap<>();

        for (Integer key : mapM.keySet()) {
            List<IndexPoint> list1 = new ArrayList<>();
            mapM.get(key).forEach(point->{

                IndexPoint indexPoint = new IndexPoint();
                indexPoint.setId(point.getIdentifies());
                indexPoint.setPoint(new Point2D.Double(point.getPointX().doubleValue(),point.getPointY().doubleValue()));

                list1.add(indexPoint);

            });
            monitorMap.put(key,list1);
        }

        log.info("monitor--end");

//        -------------------------------------------------------------------------------------------------------------------------------------------

        QueryWrapper<BusVehicleNetworkPositionRecord> queryWrapper = new QueryWrapper();
        queryWrapper.select("car_location_num").groupBy("car_location_num");
        List<BusVehicleNetworkPositionRecord> list1 = busVehicleNetworkPositionRecordService.list(queryWrapper);

        Map<Integer,List<BusVehicleNetworkPositionRecord>> map = new HashMap<>();

        for (BusVehicleNetworkPositionRecord record : list1) {
            QueryWrapper<BusVehicleNetworkPositionRecord> tmp = new QueryWrapper();
//            tmp.eq("car_location_num",record.getCarLocationNum());
            tmp.eq("car_location_num",record.getCarLocationNum()).eq("region_id",0);
//            tmp.eq("car_location_num",record.getCarLocationNum()).eq("region_id",14);
            List<BusVehicleNetworkPositionRecord> tmpList = busVehicleNetworkPositionRecordService.list(tmp);
            map.put(record.getCarLocationNum(),tmpList);
        }

        for (Integer locationNum : map.keySet()) {
            List<BusVehicleNetworkPositionRecord> recordList = map.get(locationNum);
            for (int i = 0; i < recordList.size(); i++) {
                Point2D.Double point = new Point2D.Double(recordList.get(i).getLocationX().doubleValue(),recordList.get(i).getLocationY().doubleValue());

                for (Integer regionId : regionMap.keySet()) {
                    if (PointUtils.IsPtInPoly(point,regionMap.get(regionId))) {
                        List<IndexPoint> indexPoints = monitorMap.get(regionId);
                        IndexPoint monitorPointPro = PointUtils.findMonitorPointPro(point, indexPoints, 12);

                        recordList.get(i).setRegionId(regionId);
                        recordList.get(i).setIdentifies(monitorPointPro.getId());
//                        recordList.get(i).setPointX(BigDecimal.valueOf(monitorPointPro.getPoint().getX()));
//                        recordList.get(i).setPointY(BigDecimal.valueOf(monitorPointPro.getPoint().getY()));
                    }
                }
            }
            busVehicleNetworkPositionRecordService.updateBatchById(recordList);
        }

        log.info("record--end");

    }


    @Test
    void test5(){
        List<Object> ss = busRegionSubMapper.ss();

    }

}
