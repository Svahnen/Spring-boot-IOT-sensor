package com.assignment.sensor.websock;

import com.assignment.sensor.DAO.Dao;
import com.assignment.sensor.Modules.Temp;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import java.sql.SQLNonTransientConnectionException;
import org.springframework.web.bind.annotation.ExceptionHandler;

// shamelessly stolen without credit

@Component
public class LiveUpdateHandler extends TextWebSocketHandler {
    static ObjectMapper m = new ObjectMapper();
    //    static Gson g = new Gson();
    Dao dao = new Dao();

    public LiveUpdateHandler() throws SQLException {
        dao.connect();
    }

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

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage msg) throws IOException, SQLException {
        var last_temp = m.readValue(msg.getPayload(), Integer.class);
        var cur = new Dao.Cursor();
        cur.newest = last_temp;
        var blah = dao.getNewer(cur);
        session.sendMessage(new TextMessage(m.writeValueAsString(blah)));
    }

    public void sendToAll(Temp temp) throws InterruptedException, IOException {
        var foo = new TextMessage(m.writeValueAsString(temp));
        for (WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(foo);
        }
    }

    @ExceptionHandler(SQLNonTransientConnectionException.class)
    public void handleSQLDisconnect() throws SQLException {
        dao.connect();
    }
}