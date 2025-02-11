package com.sangyunpark99.chatservice.repository;

import com.sangyunpark99.chatservice.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageJpaRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByChatroomId(Long id);

    Boolean existsByChatroomIdAndCreatedAtAfter(Long chatroomId, LocalDateTime lastCheckedAt);
}
