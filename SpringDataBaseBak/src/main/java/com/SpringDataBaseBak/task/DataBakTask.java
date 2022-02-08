package com.SpringDataBaseBak.task;

import com.SpringDataBaseBak.domain.DataBaseBakConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author chenming
 * @description
 * @create: 2022-02-08
 */
@Component
@Slf4j
public class DataBakTask {

    @Autowired
    private DataBaseBakConfig dataBaseBakConfig;

    @Scheduled(fixedRate = 10000)
    public void BakTask(){
      log.info(dataBaseBakConfig.getMysqlPath());
    }

}
