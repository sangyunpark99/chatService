package com.sangyunpark99.chatservice.repository;

import com.sangyunpark99.chatservice.entities.MemberChatRoomMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberChatRoomMappingJpaRepository extends JpaRepository<MemberChatRoomMapping, Long> {
    boolean existsByMemberIdAndChatroomId(Long memberId, Long chatroomId);

    void deleteByMemberIdAndChatroomId(Long memberId, Long chatroomId);

    @Query("select m from MemberChatRoomMapping m join fetch m.chatroom where m.member.id = :memberId")
    List<MemberChatRoomMapping> findAllByMemberId(Long memberId);
}
