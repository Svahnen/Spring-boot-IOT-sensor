package com.assignment.sensor.websock;

import com.assignment.sensor.Modules.Temp;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// shamelessly stolen without credit

@Component
public class LiveUpdateHandler extends TextWebSocketHandler {
    static Gson g = new Gson();

    //Thread safe list
    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //the messages will be broadcasted to all users.
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    public void sendToAll(Temp temp) throws InterruptedException, IOException {
        var foo = new TextMessage(g.toJson(temp));
        for (WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(foo);
        }
    }
}