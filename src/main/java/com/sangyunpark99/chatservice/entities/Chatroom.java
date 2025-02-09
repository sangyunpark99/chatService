package com.sangyunpark99.chatservice.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chatroom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    private String title;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "chatroom")
    private Set<MemberChatRoomMapping> memberChatRoomMappings = new HashSet<>();

    @Builder
    public Chatroom(String title, LocalDateTime createdAt) {
        this.title = title;
        this.createdAt = createdAt;
    }
}
