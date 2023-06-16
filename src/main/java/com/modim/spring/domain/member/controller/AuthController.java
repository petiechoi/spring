package com.modim.spring.domain.member.controller;


import com.modim.spring.domain.member.dto.MemberDto;
import com.modim.spring.domain.member.model.TokenResponseDto;
import com.modim.spring.domain.member.service.AuthService;
import com.modim.spring.global.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthStatus;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthController {

    private final AuthService authService;

    //사용자가 로그인을 시도하면 /api/authenticate로 user login 정보와 함께 요청된다. 실질적인 login 처리는 AuthService에서 이루어진다.
    @PostMapping("/authenticate")
    public ResponseEntity<TokenResponseDto> authorize(@Valid @RequestBody MemberDto.loginDto loginDto){
        TokenResponseDto tokenResponseDto = authService.login(loginDto);

        // 1. Response Header에 token 값을 넣어준다.
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenResponseDto.getToken());

        // 2. Response Body에 token 값을 넣어준다.
        return new ResponseEntity<>(tokenResponseDto, httpHeaders, HttpStatus.OK);
    }


}
