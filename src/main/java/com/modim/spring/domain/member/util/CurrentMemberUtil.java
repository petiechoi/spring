package com.modim.spring.domain.member.util;

import com.modim.spring.domain.member.model.Member;
import com.modim.spring.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentMemberUtil {
    private final MemberRepository memberRepository;

    // OUT: Member Loginid
    public static String GetCurrentMemberId(){
        String id="";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if( principal instanceof UserDetails ){
            id = ((UserDetails)principal).getUsername();
        }
        return id;
    }

    // OUT: Member
    public Member getCurrentMember() {
        String id = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if( principal instanceof UserDetails ){
            id = ((UserDetails)principal).getUsername();
        } else {
            id = principal.toString();
        }
        return memberRepository.findByloginId(id)
                .orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }
}
