package com.sangyunpark99.chatservice.controllers;

import com.sangyunpark99.chatservice.entities.Chatroom;
import com.sangyunpark99.chatservice.services.ChatService;
import com.sangyunpark99.chatservice.vos.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public Chatroom createChatroom(@AuthenticationPrincipal CustomOAuth2User user, @RequestParam String title) {
        return chatService.createChatRoom(user.getMember(),title);
    }

    @PostMapping("/{chatroomId}")
    public Boolean joinChatroom(@AuthenticationPrincipal CustomOAuth2User user, @PathVariable Long chatroomId) {
        return chatService.joinChatRoom(user.getMember(), chatroomId);
    }

    @DeleteMapping("/{chatroomId}")
    public Boolean leaveChatroom(@AuthenticationPrincipal CustomOAuth2User user, @PathVariable Long chatroomId) {
        return chatService.leaveChatroom(user.getMember(), chatroomId);
    }

    @GetMapping
    public List<Chatroom> getChatrooms(@AuthenticationPrincipal CustomOAuth2User user) {
        return chatService.getChatRooms(user.getMember());
    }
}
