package com.SpringWebSocket.websocket;

import javax.websocket.Session;

public class WebSocketClient {
    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
