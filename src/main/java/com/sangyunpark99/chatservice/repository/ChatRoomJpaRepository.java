package com.sangyunpark99.chatservice.repository;

import com.sangyunpark99.chatservice.entities.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomJpaRepository extends JpaRepository<Chatroom, Long> {
}
