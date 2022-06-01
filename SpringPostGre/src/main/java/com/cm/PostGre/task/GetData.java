package com.cm.PostGre.task;

import com.cm.PostGre.domain.BusPointDO;
import com.cm.PostGre.redis.service.RedisService;
import com.cm.PostGre.service.BusRegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.plaf.ProgressBarUI;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author chenming
 * @description
 * @create: 2022-04-17
 */
@EnableScheduling
@Component
@Slf4j
public class GetData {

    @Autowired
    private RedisService redisService;

    @Autowired
    private BusRegionService busRegionService;

    @Scheduled(cron = "0/1 * * * * ?")
    public void test() {
        Map<String, Object> location = redisService.getCacheObject("card:card84306");
        BigDecimal x = new BigDecimal(String.valueOf(location.get("x"))).add(BigDecimal.valueOf(37416000));
        BigDecimal y = new BigDecimal(String.valueOf(location.get("y"))).add(BigDecimal.valueOf(4376000));
        String s = busRegionService.queryRegionName(x, y, BigDecimal.valueOf(12));
        BusPointDO pointDO = busRegionService.pointCorrection(x, y, BigDecimal.valueOf(12));
        log.info("区域名：{}", s);
        log.info("标准回归点：{}",pointDO.toString());
    }

}
