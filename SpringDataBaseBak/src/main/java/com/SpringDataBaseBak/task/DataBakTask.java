package com.SpringDataBaseBak.task;

import com.SpringDataBaseBak.domain.DataBaseBakConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author chenming
 * @description
 * @create: 2022-02-08
 */
@Component("DataBakTask")
@Slf4j
public class DataBakTask {

    @Autowired
    private DataBaseBakConfig dataBaseBakConfig;

    public void task(){
      log.info(dataBaseBakConfig.getMysqlPath());
    }

}
