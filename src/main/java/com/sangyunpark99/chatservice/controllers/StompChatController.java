package com.sangyunpark99.chatservice.controllers;

import com.sangyunpark99.chatservice.controllers.dto.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Slf4j
@Controller
public class StompChatController {

    @MessageMapping("/chats") // /pub/chats
    @SendTo("/sub/chats") // 입력한 구독경로로 메시지를 전달
    public ChatMessage handleMessage(@AuthenticationPrincipal Principal principal,
                                     @Payload Map<String, String> payload) {
        log.info("{} send {}", principal.getName(), payload);

        // /pub/chats로 메시지를 발행하게 된다면, 저 경로로 발행된 메시지들은 이 메시지 맵핑 chats로 되어 있는 곳으로 전달되게 됩니다.
        return new ChatMessage(principal.getName(),payload.get("message"));
    }
}
