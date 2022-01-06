package com.shardingSphere.task;

import com.shardingSphere.domain.BusCarNumSip;
import com.shardingSphere.domain.BusCarOperation;
import com.shardingSphere.mapper.BusCarOperationMapper;
import com.shardingSphere.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class task {

    @Autowired
    private RedisService redisService;

    @Autowired
    private BusCarOperationMapper busCarOperationMapper;

    @Scheduled(fixedRate = 1000)
    public void te() {

        // 车辆运行记录添加
        BusCarOperation busCarOperation = new BusCarOperation();
        busCarOperation.setSiteX("2");
        busCarOperation.setSiteY("3");
        busCarOperation.setRecordDate(new Date());
        busCarOperationMapper.insertBusCarOperation(busCarOperation);
    }
}
