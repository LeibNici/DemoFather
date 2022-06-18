package com.SpringWebSocket.task;

import com.SpringWebSocket.redis.service.RedisService;
import com.SpringWebSocket.websocket.WebSocketClient;
import com.SpringWebSocket.websocket.WebSocketService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenming
 * @description
 * @create: 2022-06-10
 */
@Component
@EnableScheduling
@Slf4j
public class pushData {

    @Autowired
    private RedisService redisService;

    @Scheduled(cron = "0/1 * * * * ?")
    public void test(){
        List<Object> cacheList = redisService.getCacheList(redisService.keys("card:card*"));
        ConcurrentHashMap<String, WebSocketClient> webSocketClientConcurrentHashMap = WebSocketService.getWebSocketMap();
        for (String key :webSocketClientConcurrentHashMap.keySet()) {
            WebSocketService.sendMessage(key, JSON.toJSONString(cacheList));
        }
        log.info(JSON.toJSONString(cacheList));
    }

}
