package com.SpringMQ.Provider;

import com.SpringMQ.config.MQconfig;
import com.SpringMQ.config.Sender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProviderTask {

    @Scheduled(fixedDelay = 1000)
    public void tssk() {
        log.info("发送消息");

        for (int i = 0; i < 3; i++) {
            Sender.sendMsg(MQconfig.MY_ECXCHANGE, "my.route." + i, i);
        }
    }

}
