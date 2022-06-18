package com.cm.GeoToolsAStart.task;

import com.cm.GeoToolsAStart.Utils.GeometryUtil;
import com.cm.GeoToolsAStart.mapper.BusRegionMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.http11.filters.VoidInputFilter;
import org.geotools.graph.structure.Graph;
import org.geotools.graph.structure.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenming
 * @description
 * @create: 2022-06-18
 */
@Component
@EnableScheduling
@Slf4j
public class AStartTask {

    @Autowired
    private BusRegionMapper busRegionMapper;

    private static Graph graph = null;
    List<Node> nodes = new ArrayList<>();

    @PostConstruct
    public void init() {
        List<String> wkts = busRegionMapper.selectList();
        graph = GeometryUtil.createGraph(wkts);
        nodes = new ArrayList<>(graph.getNodes());
    }

    @Scheduled(cron = "0/3 * * * * ?")
    public void task() throws Exception {
        Random r = new Random();
        Node start = nodes.get(r.nextInt(150));
        Node end = nodes.get(r.nextInt(150));
        long startT = System.currentTimeMillis();
        List<Point2D> point2DS = GeometryUtil.AStarShortestPath(start, end, graph);
        long endT = System.currentTimeMillis();
        log.info("查询节点：{} -> {},节点数为:{}, 共耗时：{}ms", start, end, point2DS.size(), endT - startT);
    }

}
