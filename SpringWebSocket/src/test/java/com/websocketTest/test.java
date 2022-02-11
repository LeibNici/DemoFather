package com.websocketTest;

import com.SpringWebSocket.WebSocketApplication;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

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
//            webSocketClient.send("111");
            Thread.sleep(1000);
            log.info("----------------------");
        }
    }


}
