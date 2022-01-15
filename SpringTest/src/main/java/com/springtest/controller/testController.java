package com.springtest.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springtest.domain.BusCarEnterMine;
import com.springtest.domain.BusCarOperation;
import com.springtest.domain.BusCarOperation_11;
import com.springtest.domain.BusCarOperation_12;
import com.springtest.mapper.TableCheckMapper;
import com.springtest.service.BusCarOperationService11;
import com.springtest.service.Impl.BusCarEnterMineServiceImpl;
import com.springtest.service.Impl.BusCarOperationServiceImpl;
import com.springtest.service.Impl.BusCarOperationServiceImpl11;
import com.springtest.service.Impl.BusCarOperationServiceImpl12;
import javafx.beans.binding.ObjectExpression;
import lombok.extern.slf4j.Slf4j;
import org.omg.CosNaming.IstringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class testController {

    @Autowired
    private BusCarEnterMineServiceImpl busCarEnterMineService;

    @Autowired
    private BusCarOperationServiceImpl busCarOperationService;

    @Autowired
    private BusCarOperationServiceImpl12 busCarOperationServiceImpl12;

    @Autowired
    private BusCarOperationServiceImpl11 busCarOperationServiceImpl11;

    TreeMap<Integer, Object> result = new TreeMap<>();

    @GetMapping("/test")
    public Object tes() {

        Calendar calendar = Calendar.getInstance();

        QueryWrapper<BusCarEnterMine> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("record_date", "2021-12-02 00:00:00", "2021-12-12 00:00:00")
                .isNotNull("out_date")
                .last("and record_date < out_date");
        List<BusCarEnterMine> enterMineList = busCarEnterMineService.list(queryWrapper);
        enterMineList.forEach(busCarEnterMine -> {

            List<BusCarOperation_12> save = new ArrayList<>();

            Date recordDate = busCarEnterMine.getRecordDate();
            Date outDate = busCarEnterMine.getOutDate();

            calendar.setTime(recordDate);

            if (outDate != null && outDate.after(recordDate)) {
                int i = new Random().nextInt(result.size());
                List<BusCarOperation> o = (List<BusCarOperation>) result.get(i);

                o.forEach(busCarOperation -> {
                    for (int j = 0; j < 2; j++) {
                        BusCarOperation_12 current = new BusCarOperation_12();
                        current.setCarNum(busCarEnterMine.getCarNum());
                        current.setSiteX(busCarOperation.getSiteX());
                        current.setSiteY(busCarOperation.getSiteY());
                        current.setRecordDate(calendar.getTime());
                        save.add(current);
                    }
                    calendar.add(Calendar.SECOND, +1);

                });
                busCarOperationServiceImpl12.saveBatch(save);
            }

        });

        return enterMineList.size();
    }

    @GetMapping("/getoperation")
    public Object tes1() throws ParseException {
        List<BusCarOperation> operationList = busCarOperationService.list(null);
        Map<String, List<BusCarOperation>> collect = operationList.stream().collect(Collectors.groupingBy(busCarOperation -> busCarOperation.getCarNum()));
        int i = 0;
        for (String key : collect.keySet()) {
            List<BusCarOperation> carOperations = collect.get(key);
            result.put(i, carOperations);
            i++;
        }
        return "ok";
    }

    @GetMapping("/test2")
    public Object tes2() throws ParseException {
        QueryWrapper<BusCarOperation_11> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_num", "石R-20010").between("record_date", "2021-11-24 15:31:18.0", "2021-11-28 06:01:20.0").orderByDesc("record_date");
        log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS'Z'").format(System.currentTimeMillis()));
        List<BusCarOperation_11> list = busCarOperationServiceImpl11.list(queryWrapper);
        log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS'Z'").format(System.currentTimeMillis()));
        return list;
    }

    @Autowired
    private TableCheckMapper checkMapper;

    @GetMapping("/test3")
    public Object tes3() throws ParseException {
        List<BusCarOperation_11> busCarOperation_11s = checkMapper.selectSql("select bco.id,\n" +
                "    bco.car_num,\n" +
                "    bco.site_x,\n" +
                "    bco.site_y,\n" +
                "    bco.area_num,\n" +
                "    bco.record_date,\n" +
                "    bco.area_location,\n" +
                "    bco.card_number\n" +
                "    from bus_car_operation_202111 bco\n" +
                "    WHERE  bco.car_num = '石R-20010'\n" +
                "    and (bco.record_date between '2021-11-24 15:31:18.0' and '2021-11-28 06:01:20.0')\n" +
                "    order by bco.record_date desc");
        return busCarOperation_11s;
    }
}
