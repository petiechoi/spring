package com.modim.spring.domain.member.controller;


import com.modim.spring.domain.member.dto.MemberDto.loginDto;
import com.modim.spring.domain.member.model.TokenResponseDto;
import com.modim.spring.domain.member.service.AuthService;
import com.modim.spring.global.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthController {

    private final AuthService authService;

    @Value("${cookie.name}")
    private String coockieName;

    //사용자가 로그인을 시도하면,,, 실질적인 login 처리는 AuthService에서 이루어진다. 시바 ㄹ누가 클래스 이름 시작문자를 소문자로 하냐 등시나
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> authorize(@Valid loginDto loginDto, HttpServletResponse response){
        TokenResponseDto tokenResponseDto = authService.login(loginDto);
        response.addCookie(setCookie(tokenResponseDto.getToken()));
        response.setHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenResponseDto.getToken());

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenResponseDto.getToken());
        return ResponseEntity.ok(tokenResponseDto);
    }

//    쿠키를 가져올땐 HttpServletRequest 에서 가져오고,
//
//    쿠키를 설정할땐 HttpServletResponse로 설정한다.

    @PostMapping("/logout")
    public void logout(HttpServletResponse response){
        response.addCookie(delCookie());
    }



    public Cookie setCookie(String coockieValue)
    {
        Cookie cookie = new Cookie(coockieName, coockieValue);
        cookie.setMaxAge(60 * 60 * 24 * 30);    // 30 days
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

    public Cookie delCookie(){
        Cookie cookie = new Cookie(coockieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }
}