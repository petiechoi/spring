package com.modim.spring.domain.member.controller;


import com.modim.spring.domain.member.dto.MemberDto.loginDto;
import com.modim.spring.domain.member.model.TokenResponseDto;
import com.modim.spring.domain.member.service.AuthService;
import com.modim.spring.global.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    //사용자가 로그인을 시도하면,,, 실질적인 login 처리는 AuthService에서 이루어진다.
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> authorize(@Valid @RequestBody loginDto loginDto, HttpServletResponse response){
        Cookie cookie = new Cookie("Community", "modim");
        cookie.setMaxAge(60 * 60 * 24 * 30);    // 30days
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        TokenResponseDto tokenResponseDto = authService.login(loginDto);
        // 1. Response Header에 token 값을 넣어준다.
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenResponseDto.getToken());

        // 2. Response Body에 token 값을 넣어준다.
        //return new ResponseEntity<>(tokenResponseDto, httpHeaders, HttpStatus.OK);
        return ResponseEntity.ok(tokenResponseDto);
    }
}
