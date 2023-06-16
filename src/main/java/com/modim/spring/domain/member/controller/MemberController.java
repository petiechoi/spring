package com.modim.spring.domain.member.controller;

import com.modim.spring.domain.member.dto.MemberDto.RequestDto;
import com.modim.spring.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public Long join(@Valid @RequestBody RequestDto requestDto) {
        return memberService.signup(requestDto);
    }

    // 로그인 API
//    @PostMapping("/login")
//    public String login(@RequestBody MemberDto memberDto) {
//        return memberService.login(memberDto);
//    }

}
