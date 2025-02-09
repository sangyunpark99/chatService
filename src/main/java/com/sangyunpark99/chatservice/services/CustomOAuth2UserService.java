package com.sangyunpark99.chatservice.services;

import com.sangyunpark99.chatservice.entities.Member;
import com.sangyunpark99.chatservice.entities.enums.Gender;
import com.sangyunpark99.chatservice.repository.MemberJpaRepository;
import com.sangyunpark99.chatservice.vos.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttribute("kakao_account");
        String email = (String) attributes.get("email"); // down casting

        Member member = memberJpaRepository.findByEmail(email).orElseGet(() -> registerMember(attributes));
        CustomOAuth2User user = new CustomOAuth2User(member, attributes);

        return user;
    }

    @Transactional
    public Member registerMember(Map<String, Object> attributeMap) {
        Member member = Member.builder()
                .email((String) attributeMap.get("email"))
                .nickName((String)((Map) attributeMap.get("profile")).get("nickname"))
                .name((String) attributeMap.get("name"))
                .phoneNumber((String) attributeMap.get("phone_number"))
                .gender(Gender.valueOf(((String) attributeMap.get("gender")).toUpperCase()))
                .birthDay(getBirthDay(attributeMap))
                .role("USER_ROLE")
                .build();

        return memberJpaRepository.save(member);
    }

    private LocalDate getBirthDay(Map<String, Object> attributeMode) {
        String birthYear = (String) attributeMode.get("birthyear");
        String birthDay = (String) attributeMode.get("birthday");

        return LocalDate.parse(birthYear + birthDay, DateTimeFormatter.BASIC_ISO_DATE);
    }
}