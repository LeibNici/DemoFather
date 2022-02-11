package com.SpringWebSocket.websocket;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@Slf4j
public class WebsocketConfig {

    @Bean
    public WebSocketClient webSocketClient() throws URISyntaxException {
        WebSocketClient client = new WebSocketClient(new URI("ws://127.0.0.1:9206/websocket/appSpeed4a48e4")) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                log.info("连接成功！");
            }

            @Override
            public void onMessage(String s) {
                log.info("收到消息：{}", s);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                log.info("连接关闭！");
            }

            @Override
            public void onError(Exception e) {
                log.info("连接异常！");
            }
        };

        client.connect();
        return client;
    }

}
