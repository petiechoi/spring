package com.modim.spring.domain.member.service;

import com.modim.spring.domain.member.dto.MemberDto;
import com.modim.spring.domain.member.model.TokenResponseDto;
import com.modim.spring.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public TokenResponseDto login(MemberDto.loginDto loginDto){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getLoginId(), loginDto.getLoginPassword());


        // 아직 인증이 완료되지 않은 객체에 authenticationManagerBuilder를 활용하여 AuthenticationManager의 구현체인
        // ProviderManger의 authenticate 메소드를 실행하여 검증 후 Authenication 객체를 받는다.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // authentication을 기준으로 jwt token 생성
        String jwt = jwtTokenProvider.createToken(authentication);

        return new TokenResponseDto(jwt);

    }
}
