package com.springtest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.springtest.domain.BusCarEnterMine;
import com.springtest.domain.BusCarOperation;
import com.springtest.domain.RemotePoint;
import com.springtest.service.Impl.BusCarEnterMineServiceImpl;
import com.springtest.service.Impl.BusCarOperationServiceImpl;
import com.springtest.service.Impl.RemotePointServiceImpl;
import com.sun.deploy.net.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@Slf4j
public class MainTest {

    @Autowired
    private BusCarEnterMineServiceImpl busCarEnterMineService;

    @Autowired
    private BusCarOperationServiceImpl busCarOperationService;

    @Test
    public void test() {

        QueryWrapper<BusCarOperation> operationQueryWrapper = new QueryWrapper<>();


        QueryWrapper<BusCarEnterMine> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("record_date", "2021-11");
        List<BusCarEnterMine> list = busCarEnterMineService.list(queryWrapper);

    }

    @Test
    public void test1() throws ParseException {

        Date da1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-13 15:39:30");
        Date da2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-13 15:49:46");

        System.out.println((da2.getTime() - da1.getTime()) / 1000);

    }

    @Test
    public void test3() throws ParseException {

        String s = "1,2,3,4";
        String[] split = s.split(",");
        UpdateWrapper<BusCarOperation> updateWrapper = new UpdateWrapper();
        updateWrapper.in("id", split).set("deleted_status", "1");
        busCarOperationService.update(null, updateWrapper);
    }

    @Autowired
    private RemotePointServiceImpl remotePointService;

    @Test
    public void test4() throws Exception {
        String result = "";
        HttpURLConnection connection = (HttpURLConnection) new URL("https://mock.apipost.cn/app/mock/project/916f248a-1033-44e7-9170-337f4c72a33d/get").openConnection();
        connection.setRequestMethod("GET");
        InputStream is = connection.getInputStream();
        //构造一个字符流缓存
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String str = "";
        while ((str = br.readLine()) != null) {
            result += str;
        }
        Map<String, Object> jsonObject = JSON.parseObject(result);
        Map<String, JSONArray> data = (Map<String, JSONArray>) jsonObject.get("data");
        JSONArray point2DS = data.get("0");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-10-30 09:19:37"));

        List<RemotePoint> remotePointList = new ArrayList<>();

        point2DS.forEach(point -> {
            RemotePoint remotePoint = new RemotePoint();
            String[] split = point.toString().substring(1, point.toString().length() - 1).split(",");
            remotePoint.setX(Double.parseDouble(split[0]));
            remotePoint.setY(Double.parseDouble(split[1]));
            calendar.add(Calendar.SECOND, +1);
            remotePoint.setRecordDate(calendar.getTime());
            remotePointList.add(remotePoint);
        });

        remotePointService.saveBatch(remotePointList);

    }

    @Test
    public void test5() throws Exception {

        Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-10-30 09:50:10");

        List<RemotePoint> list = remotePointService.list();
        List<RemotePoint> collect = list.stream().filter(remotePoint -> remotePoint.getRecordDate().after(end)).collect(Collectors.toList());
        System.out.println(collect.size());
    }

}
