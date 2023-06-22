package com.modim.spring.global.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@Component
//import javax.servlet의 Filter
public class JwtFilter implements Filter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtTokenProvider jwtTokenProvider;

    // 실제 필터링 로직은 doFilter 내부에 작성 jwt 토큰의 인증 정보를 SecurityContext에 저장하는 역할
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        // request에서 jwt 토큰 추출
        String jwt = resolveToken(httpServletRequest);
        String requestURL = httpServletRequest.getRequestURI();

        // token 유효성검증에 통과 후
        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)){
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            //log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        }else {
            //log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }

        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

