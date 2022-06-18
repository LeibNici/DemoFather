package com.websocketTest;

import com.SpringWebSocket.WebSocketApplication;
import com.SpringWebSocket.websocket.WebSocketClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.*;

/**
 * @author chenming
 * @description
 * @create: 2022-02-11
 */
@SpringBootTest(classes = WebSocketApplication.class)
@Slf4j
public class test {

    @Autowired
    private WebSocketClient webSocketClient;

    @Test
    public void test() throws InterruptedException {
        while (true) {
            log.info("发送消息！");
            Thread.sleep(1000);
            log.info("----------------------");
        }
    }

    Map<Integer,ProcessBuilder> map = new HashMap<>();
    @Test
    public void test1() throws Exception {
        String cmd = "ping www.baidu.com -t";
        Process exec = Runtime.getRuntime().exec("cmd /c start cmd.exe /c \"" + cmd + "\"");
        log.info("----------------------");
    }

    @Test
    public void test2() throws IOException, InterruptedException {
        String cmd = "ping www.baidu.com -t";
        List<String> cmdList = new ArrayList<>();
        cmdList.add("cmd");
        cmdList.add("/c");
        cmdList.add("start");
        cmdList.add("cmd.exe");
        cmdList.add("/c");
        cmdList.add(cmd);

        ProcessBuilder processBuilder =new ProcessBuilder(cmdList);
        map.put(1,processBuilder);
        processBuilder.start();
        Thread.sleep(100000);
        log.info("----------------------");
    }

    @Test
    public void test3() throws IOException, InterruptedException {
        log.info(String.valueOf(map.size()));
        ProcessBuilder processBuilder = map.get(1);
        log.info("----------------------");
    }

    @Test
    public void test4(){
        List<Integer> list = new ArrayList<>(Arrays.asList(2,3,5,8,3,5,2));
        List<Integer> add = new ArrayList<>(Arrays.asList(10,101,22));
        for (int i = 0; i < add.size(); i++) {
            list.add(3+i,add.get(i));
        }
        log.info("----------------------");
    }

    @Test
    public void test5(){
        Map<String, Integer> map = new HashMap<>();
        map.put("11",22222);
        log.info("----------------------");
    }

}
