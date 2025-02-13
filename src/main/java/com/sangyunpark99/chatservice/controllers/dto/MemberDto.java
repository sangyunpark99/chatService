package com.sangyunpark99.chatservice.controllers.dto;

import com.sangyunpark99.chatservice.entities.Member;
import com.sangyunpark99.chatservice.entities.enums.Gender;
import com.sangyunpark99.chatservice.entities.enums.Role;

import java.time.LocalDate;

public record MemberDto(
        Long id,
        String email,
        String name,
        String password,
        String confirmPassword,
        String nickName,
        Gender gender,
        String phoneNumber,
        LocalDate birthday,
        Role role
) {

    public static MemberDto from(Member member) {
        return new MemberDto(
                member.getId(),
                member.getEmail(),
                member.getName(),
                null,
                null,
                member.getNickName(),
                member.getGender(),
                member.getPhoneNumber(),
                member.getBirthDay(),
                member.getRole()
        );
    }

    public static Member to(MemberDto memberDto) {
        return Member.builder()
                .id(memberDto.id)
                .nickName(memberDto.nickName)
                .email(memberDto.email)
                .name(memberDto.name)
                .gender(memberDto.gender)
                .phoneNumber(memberDto.phoneNumber)
                .birthDay(memberDto.birthday)
                .role(memberDto.role)
                .build();
    }
}
