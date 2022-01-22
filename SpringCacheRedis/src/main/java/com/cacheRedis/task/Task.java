package com.cacheRedis.task;

import com.alibaba.fastjson.JSON;
import com.cacheRedis.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author chenming
 * @description redis追加操作
 * @create: 2022-01-22
 */
@Component
public class Task {

    @Autowired
    private RedisService redisService;

    private Queue<Map<String, Object>> queue = new LinkedList<Map<String, Object>>();

    @Scheduled(fixedRate = 1000)
    public void s() throws ExecutionException, InterruptedException {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("date", new Date());
        queue.add(map);
        if (queue.size()==20){
            FutureTask<List<Object>> futureTask = new FutureTask<>(new PointQueue(queue));
            Thread thread = new Thread(futureTask);
            thread.start();
        }
    }

    class PointQueue implements Callable<List<Object>>{

        private Queue<Map<String, Object>> queue;

        public PointQueue(Queue<Map<String, Object>> queue) {
            this.queue = queue;
        }

        @Override
        public List<Object> call() throws Exception {
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < queue.size(); i++) {
                list.add(queue.poll());
            }
            return list;
        }
    }


}
