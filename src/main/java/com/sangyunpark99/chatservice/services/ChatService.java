package com.sangyunpark99.chatservice.services;

import com.sangyunpark99.chatservice.entities.Chatroom;
import com.sangyunpark99.chatservice.entities.Member;
import com.sangyunpark99.chatservice.entities.MemberChatRoomMapping;
import com.sangyunpark99.chatservice.repository.ChatRoomJpaRepository;
import com.sangyunpark99.chatservice.repository.MemberChatRoomMappingJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomJpaRepository chatRoomJpaRepository;
    private final MemberChatRoomMappingJpaRepository memberChatRoomMappingJpaRepository;

    @Transactional
    public Chatroom createChatRoom(Member member, String title) { // 채팅방 생성
        Chatroom chatroom = Chatroom.builder()
                .title(title)
                .createdAt(LocalDateTime.now())
                .build();

        chatroom = chatRoomJpaRepository.save(chatroom);
        memberChatRoomMappingJpaRepository.save(new MemberChatRoomMapping(member, chatroom));

        return chatroom;
    }


    public boolean joinChatRoom(Member member, Long chatroomId) { // 채팅방 유저 가입
        if (memberChatRoomMappingJpaRepository.existsByMemberIdAndChatroomId(member.getId(), chatroomId)) {
            log.info("이미 참여한 채팅방입니다!");
            return false;
        }

        Chatroom chatroom = chatRoomJpaRepository.findById(chatroomId).orElseThrow(() -> new IllegalArgumentException(
                "채팅방이 존재하지 않습니다."));

        MemberChatRoomMapping memberChatRoomMapping = MemberChatRoomMapping.builder()
                .chatroom(chatroom)
                .member(member)
                .build();

        memberChatRoomMappingJpaRepository.save(memberChatRoomMapping);

        return true;
    }

    public boolean leaveChatroom(Member member, Long chatroomId) {
        if(!memberChatRoomMappingJpaRepository.existsByMemberIdAndChatroomId(member.getId(), chatroomId)) {
            log.info("참여하지 않은 채팅방입니다.");
            return false;
        }

        memberChatRoomMappingJpaRepository.deleteByMemberIdAndChatroomId(member.getId(), chatroomId);
        return true;
    }

    public List<Chatroom> getChatRooms(Member member) {

        List<MemberChatRoomMapping> memberChatRoomMappings =
                memberChatRoomMappingJpaRepository.findAllByMemberId(member.getId());

        return memberChatRoomMappings
                .stream().map((memberChatRoomMapping) -> memberChatRoomMapping.getChatroom()).collect(Collectors.toList());
    }
}
