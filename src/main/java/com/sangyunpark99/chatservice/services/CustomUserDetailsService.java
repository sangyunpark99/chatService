package com.sangyunpark99.chatservice.services;

import com.sangyunpark99.chatservice.controllers.dto.MemberDto;
import com.sangyunpark99.chatservice.entities.Member;
import com.sangyunpark99.chatservice.entities.enums.Role;
import com.sangyunpark99.chatservice.repository.MemberJpaRepository;
import com.sangyunpark99.chatservice.vos.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberJpaRepository.findByName(username).orElseThrow(() -> new IllegalArgumentException(
                "존재하지 않는 유저입니다."));
        if(member.getRole() != Role.ROLE_CONSULTANT) {
            throw new AccessDeniedException("상담사가 이닙니다.");
        }

        return new CustomUserDetails(member);
    }

    public MemberDto saveMember(MemberDto memberDto) {

        Member member = MemberDto.to(memberDto);
        member.updatePassword(memberDto.password(), memberDto.confirmPassword(), passwordEncoder);

        member = memberJpaRepository.save(member);

        return MemberDto.from(member);
    }
}
