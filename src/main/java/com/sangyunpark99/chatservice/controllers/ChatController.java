package com.sangyunpark99.chatservice.controllers;

import com.sangyunpark99.chatservice.controllers.dto.ChatMessage;
import com.sangyunpark99.chatservice.controllers.dto.ChatRoomDto;
import com.sangyunpark99.chatservice.entities.Chatroom;
import com.sangyunpark99.chatservice.entities.Message;
import com.sangyunpark99.chatservice.services.ChatService;
import com.sangyunpark99.chatservice.vos.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatRoomDto createChatroom(@AuthenticationPrincipal CustomOAuth2User user,
                                      @RequestParam(name = "title") String title) {
        Chatroom chatroom = chatService.createChatRoom(user.getMember(), title);
        return ChatRoomDto.from(chatroom);
    }

    @PostMapping("/{chatroomId}")
    public Boolean joinChatroom(@AuthenticationPrincipal CustomOAuth2User user, @PathVariable Long chatroomId,
                                @RequestParam(required = false) Long currentChatroomId) {
        return chatService.joinChatRoom(user.getMember(), chatroomId, currentChatroomId);
    }

    @DeleteMapping("/{chatroomId}")
    public Boolean leaveChatroom(@AuthenticationPrincipal CustomOAuth2User user, @PathVariable Long chatroomId) {
        return chatService.leaveChatroom(user.getMember(), chatroomId);
    }

    @GetMapping
    public List<ChatRoomDto> getChatrooms(@AuthenticationPrincipal CustomOAuth2User user) {
        return chatService.getChatRooms(user.getMember()).stream().map(ChatRoomDto::from).collect(Collectors.toList());
    }

    @GetMapping("/{chatroomId}/messages")
    public List<ChatMessage> getMessages(@PathVariable(name = "chatroomId") Long chatroomId) {
        List<Message> messages = chatService.getMessages(chatroomId);
        return messages.stream().map(message -> new ChatMessage(message.getMember().getName(), message.getText())).collect(Collectors.toList());
    }
}