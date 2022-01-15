package com.springtest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springtest.domain.BusCarEnterMine;
import com.springtest.domain.BusCarOperation;
import com.springtest.service.Impl.BusCarEnterMineServiceImpl;
import com.springtest.service.Impl.BusCarOperationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

        System.out.println((da2.getTime() - da1.getTime())/1000 );

    }
}
