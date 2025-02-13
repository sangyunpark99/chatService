package com.sangyunpark99.chatservice.entities;

import com.sangyunpark99.chatservice.entities.enums.Gender;
import com.sangyunpark99.chatservice.entities.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    private String password;

    private String nickName;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phoneNumber;

    private LocalDate birthDay;

    private Role role;

    @OneToMany(mappedBy = "member")
    private Set<MemberChatRoomMapping> memberChatRoomMappings;

    @Builder
    public Member(Long id, String email, String nickName, Gender gender, String phoneNumber, LocalDate birthDay,
                  Role role, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.role = role;
    }

    public void updatePassword(String password, String confirmedPassword, PasswordEncoder passwordEncoder) {
        if(!password.equals(confirmedPassword)) {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }

        this.password = passwordEncoder.encode(password); // 인코딩한 패스워드를 저장
    }
}
