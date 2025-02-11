package com.sangyunpark99.chatservice.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberChatRoomMapping {

    @Column(name = "member_chatroom_mapping_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; // 한명의 회원은 여러개의 매핑을 가질 수 있다.

    @JoinColumn(name = "chatroom_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Chatroom chatroom; // 채팅방은 여러개의 매핑을 가질 수 있다.

    private LocalDateTime lastCheckedAt;

    @Builder
    public MemberChatRoomMapping(Member member, Chatroom chatroom) {
        this.member = member;
        this.chatroom = chatroom;
    }

    public void updateCheckedAt() {
        this.lastCheckedAt = LocalDateTime.now();
    }
}
