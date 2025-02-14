package com.sangyunpark99.chatservice.controllers;

import com.sangyunpark99.chatservice.controllers.dto.ChatRoomDto;
import com.sangyunpark99.chatservice.controllers.dto.MemberDto;
import com.sangyunpark99.chatservice.services.ConsultantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/consultants")
@Controller
public class ConsultantController {

    private final ConsultantService consultantService;

    @ResponseBody
    @PostMapping
    public MemberDto saveMember(@RequestBody MemberDto memberDto) {
        return consultantService.saveMember(memberDto);
    }

    @GetMapping
    public String index() {
        return "consultants/index.html";
    }

        @ResponseBody
        @GetMapping("/chats")
        public Page<ChatRoomDto> getChatroom(Pageable page) {
            return consultantService.getChatRoomPage(page);
        }
}
