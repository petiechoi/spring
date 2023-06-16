package com.modim.spring.domain.member.service;

import com.modim.spring.domain.member.model.Member;
import com.modim.spring.domain.member.model.Role;
import com.modim.spring.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import com.modim.spring.domain.member.dto.MemberDto.RequestDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public long signup(RequestDto requestDto){
        if (memberRepository.findByloginId(requestDto.getLoginId()).orElse(null) != null){
            throw new RuntimeException("이미 가입된 유저입니다.");
        }

        Member memberInfo = Member.builder()
                .loginId(requestDto.getLoginId())
                .loginPassword(passwordEncoder.encode(requestDto.getLoginPassword()))
                .email(requestDto.getEmail())
                .name(requestDto.getName())
                .role(Role.ROLE_USER)
                .build();
        return memberRepository.save(memberInfo).getId();
    }
}
