package com.sangyunpark99.chatservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
public class StompConfiguration implements WebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 웹 소켓 클라이언트가 어떠한 경로로 접근해야 하는가?
        // 서버와 커넥션을 맺을 수 있습니다.
        registry.addEndpoint("/stomp/chats");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지 브로커의 역할을 하기 위해서 클라이언트에 메시지를 발행을 하고, 클라이언트는 브로커로부터 전달되는 메시지를 받기 위해 구독을 신청해야 하는 경로를 지정해 주는 곳입니다.
        registry.setApplicationDestinationPrefixes("/pub"); // 메시지 publish
        registry.enableSimpleBroker("/sub"); // 구독 신청
    }

    // Stomp는 Controller를 통해서 메시지를 다루는 로직을 작성할 수 있습니다.
}
