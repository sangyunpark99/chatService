package com.sangyunpark99.chatservice.repository;

import com.sangyunpark99.chatservice.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByName(String name);

}
