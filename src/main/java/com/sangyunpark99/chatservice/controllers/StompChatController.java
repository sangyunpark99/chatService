package com.sangyunpark99.chatservice.controllers;

import com.sangyunpark99.chatservice.controllers.dto.ChatMessage;
import com.sangyunpark99.chatservice.services.ChatService;
import com.sangyunpark99.chatservice.vos.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chats/{chatroomId}") // /pub/chats
    @SendTo("/sub/chats/{chatroomId}") // 입력한 구독경로로 메시지를 전달
    public ChatMessage handleMessage(@AuthenticationPrincipal Principal principal,
                                     @DestinationVariable Long chatroomId,
                                     @Payload Map<String, String> payload) {
        log.info("{} send {} in {}", principal.getName(), payload, chatroomId);

        CustomOAuth2User user = (CustomOAuth2User) ((AbstractAuthenticationToken) principal).getPrincipal();
        chatService.saveMessage(user.getMember(), chatroomId, payload.get("message"));
        // /pub/chats로 메시지를 발행하게 된다면, 저 경로로 발행된 메시지들은 이 메시지 맵핑 chats로 되어 있는 곳으로 전달되게 됩니다.
        simpMessagingTemplate.convertAndSend("/sub/chats/news", chatroomId);
        return new ChatMessage(principal.getName(),payload.get("message"));
    }
}
