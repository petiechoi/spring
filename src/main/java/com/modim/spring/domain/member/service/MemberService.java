package com.modim.spring.domain.member.service;

import com.modim.spring.domain.member.model.Member;
import com.modim.spring.domain.member.repository.MemberRepository;
import com.modim.spring.global.response.dto.Response;
import lombok.RequiredArgsConstructor;
import com.modim.spring.domain.member.dto.MemberDto.RequestDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.modim.spring.domain.member.model.MemberCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Response signup(RequestDto requestDto){
        if (memberRepository.findByloginId(requestDto.getLoginId()).orElse(null) != null){
            return Response.error(ID_DUPLICATE.getMsg());
        }
        Member member = requestDto.toEntity(passwordEncoder);
        //memberRepository.save(member);
        return Response.success();
    }
}
