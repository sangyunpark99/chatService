package com.sangyunpark99.chatservice.entities;

import com.sangyunpark99.chatservice.entities.enums.Gender;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Column(name = "member_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickName;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phoneNumber;

    private LocalDate birthDay;

    private String role;

    @OneToMany(mappedBy = "member")
    private Set<MemberChatRoomMapping> memberChatRoomMappings;

    @Builder
    public Member(String email, String nickName, Gender gender, String phoneNumber, LocalDate birthDay,
                  String role, String name) {
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.role = role;
    }
}
