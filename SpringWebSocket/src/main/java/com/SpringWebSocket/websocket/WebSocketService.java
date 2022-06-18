package com.SpringWebSocket.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/{userName}")
@Component
public class WebSocketService {

    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);

    private static ConcurrentHashMap<String, WebSocketClient> webSocketMap = new ConcurrentHashMap<>();

    private Session session;

    private String userName = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName) {
        this.session = session;
        this.userName = userName;
        WebSocketClient client = new WebSocketClient();
        client.setSession(session);
        webSocketMap.put(userName, client);
        try {
            log.info("当前用户 {} 加入会话！", userName);
            // 连接成功提示前端
            sendMessage("来自后台的反馈：连接成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose() {

        if (webSocketMap.get(userName) != null) {

            //判断当前存放的session id 和 发起onClose 请求的id是否一致 不一致等待服务端自动关闭处理
            if (webSocketMap.get(userName).getSession().getId() == this.session.getId()) {
                webSocketMap.remove(userName);
                log.info("当前用户 {} 已被移除！", userName);
            }else {
                log.info("服务器主动发起关闭sessionID：{}，无需等待客户机确认!",this.session.getId());
            }

        } else {
            log.info("当前用户 {} 为空！", userName);
        }

    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.info("服务端主动关闭连接!");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到用户消息:" + userName + ",报文:" + message);
    }

    public void sendMessage(String message) throws IOException {
        synchronized (session) {
            this.session.getBasicRemote().sendText(message);
        }
    }

    public static void sendMessage(String userName, String message) {
        try {
            WebSocketClient webSocketClient = webSocketMap.get(userName);
            if (webSocketClient != null && webSocketClient.getSession().isOpen()) {
                webSocketClient.getSession().getBasicRemote().sendText(message);
            } else {
                log.error("网络问题导致消息不可达！");
                webSocketClient.getSession().close(new CloseReason(CloseReason.CloseCodes.RESERVED, "消息不可达！"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static ConcurrentHashMap<String, WebSocketClient> getWebSocketMap() {
        return webSocketMap;
    }

    public static void setWebSocketMap(ConcurrentHashMap<String, WebSocketClient> webSocketMap) {
        WebSocketService.webSocketMap = webSocketMap;
    }
}
