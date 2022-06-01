package com.cm.video.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenming
 * @description
 * @create: 2022-05-17
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{userName}")
public class WebsocketService {

    private static ConcurrentHashMap<String, Session> websocketMap = new ConcurrentHashMap<>();

    private String userName = "";
    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName) {
        this.userName = userName;
        this.session = session;
        websocketMap.put(userName, session);
        log.info("{} 加入连接", userName);
    }

    @OnClose
    public void onClose() {
        websocketMap.remove(userName);
    }

    public static void send(String userName, String message) throws IOException {
        websocketMap.get(userName).getBasicRemote().sendText(message);
    }

    public static ConcurrentHashMap<String, Session> getWebsocketMap() {
        return websocketMap;
    }
}
