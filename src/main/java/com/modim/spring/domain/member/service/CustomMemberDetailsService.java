package com.modim.spring.domain.member.service;

import com.modim.spring.domain.member.model.Member;
import com.modim.spring.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // DB에서 유저정보가져옴
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByloginId(username)
                .map(member -> createMemberDetails(member))
                .orElseThrow(()-> new UsernameNotFoundException(username + "존재하지 않는 Id입니다. "));
    }

    // DB에서 조회한 user 정보를 기반으로 UserDetails 을 생성하여 return 한다.
    private UserDetails createMemberDetails(Member member){
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getRole().name());

        return new User(
                member.getLoginId(),
                member.getLoginPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}
