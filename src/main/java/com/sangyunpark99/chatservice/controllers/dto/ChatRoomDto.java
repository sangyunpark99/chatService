package com.sangyunpark99.chatservice.controllers.dto;

import com.sangyunpark99.chatservice.entities.Chatroom;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatRoomDto {

    private Long id;
    private String title;
    private int memberCount;
    private boolean hasNewMessage;
    private LocalDateTime createdAt;

    @Builder
    public ChatRoomDto(Long id, String title, int memberCount, LocalDateTime createdAt, boolean hasNewMessage) {
        this.id = id;
        this.title = title;
        this.memberCount = memberCount;
        this.createdAt = createdAt;
        this.hasNewMessage = hasNewMessage;
    }

    public static ChatRoomDto from(Chatroom chatRoom) {
        return ChatRoomDto.builder()
                .id(chatRoom.getId())
                .title(chatRoom.getTitle())
                .memberCount(chatRoom.getMemberChatRoomMappings().size())
                .hasNewMessage(chatRoom.getHasNewMessage())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }
}
