package com.ruoyi.system;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.system.domain.RemotePoint;
import com.ruoyi.system.redis.service.RedisService;
import com.ruoyi.system.service.Impl.RemotePointServiceImpl;
import com.ruoyi.system.utils.CopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.midi.Soundbank;
import java.util.*;

@SpringBootTest
@Slf4j
public class springtest {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RemotePointServiceImpl remotePointService;

    @Test
    public void S() {
//        QueryWrapper<RemotePoint> queryWrapper = new QueryWrapper();
//        queryWrapper.likeRight("record_date","2021-10-30");
        List<RemotePoint> list = remotePointService.list(null);
//        System.out.println(list.size());
        redisService.setCacheObject("remotePoint", list);
    }

    @Test
    public void S1() {
        // 原始list
        List<RemotePoint> resource = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RemotePoint remotePoint = new RemotePoint();
            remotePoint.setId(i);
            resource.add(remotePoint);
        }

        List<RemotePoint> target = new ArrayList<>();
        target = CopyUtils.deepCopy(resource);

        target.forEach(remotePoint -> {
            remotePoint.setId(remotePoint.getId() + 1);
        });

        target.size();

    }

    @Test
    public void test2() {
        List<Object> a = new ArrayList<>();
        a.add(JSON.parseObject("{\"@type\":\"com.ruoyi.common.core.com.SpringPoints.domain.reportmanagement.BusCarOperation\",\"carNum\":\"转L05003\",\"carType\":\"2\",\"driverName\":\"徐克礼\",\"driverPhone\":\"9663\",\"params\":{\"@type\":\"java.util.HashMap\"}}", HashMap.class));
        redisService.setCacheList("web:carOperationList", a);
    }

    @Test
    public void os() {
        char s = '0';
        String sd = "123";

        System.out.println(sd.length() < 3 ? sd = StrUtil.fillBefore(sd, s, 3) : sd);
    }

    @Test
    public void sdd() {
        List<String> sd = new ArrayList<>();
        sd.add("1");
        sd.add("2");
        sd.add("3");

        String s1 = "";
        try {
            s1 = sd.stream().filter(s -> s.equals("22")).findAny().get();
        } catch (Exception e) {
            s1 = "ds";
        }

        System.out.println(s1);


    }

    @Test
    void test23(){
        for (int i = 0; i < 130; i++) {
            RemotePoint remotePoint = new RemotePoint();
            remotePoint.setRecordDate(new Date());
            Object o  =JSON.parseObject("{\n" +
                    "  \"@type\": \"com.bdtd.project.system.domain.BaseStation\",\n" +
                    "  \"createBy\": \"admin\",\n" +
                    "  \"createTime\": 1617240693000,\n" +
                    "  \"empCount\": 0,\n" +
                    "  \"extent1\": \"0.9998026122412449,-0.01986797814531946\",\n" +
                    "  \"extent2\": \"-0.9999594229003496,-0.009008471168843374\",\n" +
                    "  \"leftLength\": \"125.66589678920278\",\n" +
                    "  \"params\": {\n" +
                    "    \"@type\": \"java.util.HashMap\"\n" +
                    "  },\n" +
                    "  \"remark\": \"第一辅运大巷\",\n" +
                    "  \"rightLength\": \"300.68254082542234\",\n" +
                    "  \"roId\": \"161724069402527\",\n" +
                    "  \"stationId\": 41,\n" +
                    "  \"stationName\": \"消防材料库门口\",\n" +
                    "  \"stationNumber\": \"13412\",\n" +
                    "  \"stationStatus\": \"1\",\n" +
                    "  \"stationType\": \"1\",\n" +
                    "  \"updateBy\": \"admin\",\n" +
                    "  \"updateTime\": 1620873293000,\n" +
                    "  \"xCoordinate\": \"4015.8499\",\n" +
                    "  \"yCoordinate\": \"  5374.9011\",\n" +
                    "  \"zigbeeStatus\": \"1\"\n" +
                    "}",Object.class);
//            Object o = JSON.parseObject("{\"@type\":\"com.ruoyi.common.core.domain.basicinfomanage.BusCarNumSip\",\"carLogisticsTaskStatus\":\"1\",\"carNum\":\"70\",\"carNumName\":\"" + i + "\",\"carNumber\":\"转L05003\",\"carType\":\"2\",\"cardNumber\":\"84311\",\"createTime\":1627317732000,\"createUser\":\"1\",\"id\":119,\"isOnline\":1,\"locationNumber\":\"84311\",\"params\":{\"@type\":\"java.util.HashMap\"},\"sipNum\":\"49\",\"sipNumName\":\"9663\",\"updataTime\":1646729875000,\"updataUser\":\"1\",\"vtin\":\"6c2714\",\"x\":\"1311.0793303285743\",\"y\":\"6273.259761148289\"}", Object.class);
//            redisService.setCacheObject("web:fzysloc:"+i,o);
            redisService.setCacheObject("#station:station"+i,o);
//            redisService.setCacheObject("test:keys:"+i,remotePoint);
            System.out.println(i);
        }
    }

    @Test
    public void  test() throws InterruptedException {
        while (true){
            long start = System.currentTimeMillis();
            List<Object> mineList = redisService.getCacheList((Set<String>) redisService.keys("web:fzysloc*"));
            log.info(String.valueOf(System.currentTimeMillis()-start));
            Thread.sleep(1000);
        }
    }

    @Test
    public void test231() throws InterruptedException {
        while (true){
            long start = System.currentTimeMillis();
            List<Object> mineList = redisService.getCacheList((Set<String>) redisService.keys("web:fzysloc*"));
            log.info(String.valueOf(System.currentTimeMillis()-start));
            Thread.sleep(1000);
        }
    }

    @Test
    public void test31(){
        List<Integer> list = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        List<List<Integer>> partition = ListUtil.partition(list, 4);
        log.info("123");
    }

}
