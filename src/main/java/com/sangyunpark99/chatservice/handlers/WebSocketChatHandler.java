package com.sangyunpark99.chatservice.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    final Map<String, WebSocketSession> webSocketSessionMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception { // 메시지를 처리하는 로직을 작성
        log.info("{} connection", session.getId());
        webSocketSessionMap.put(session.getId(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception, IOException { // 연결을 끊었을때
        // 처리하는 로직
        log.info("{} send {]", session.getId(), message.getPayload());

        System.out.println(webSocketSessionMap.get(session.getId()));

        webSocketSessionMap.values().forEach(webSocketSession -> {
            try {
                log.info("message : {}", message);
                webSocketSession.sendMessage(new WebSocketMessage<Object>() {
                    @Override
                    public Object getPayload() {
                        return "connected";
                    }

                    @Override
                    public int getPayloadLength() {
                        return 0;
                    }

                    @Override
                    public boolean isLast() {
                        return false;
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception { // 웹소켓 클라이언트가
        log.info("{} disconnected", session.getId());
        webSocketSessionMap.remove(session.getId());
    }
}
