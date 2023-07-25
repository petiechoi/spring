package com.modim.spring.global.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ansi.Ansi8BitColor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@Component
//import javax.servlet의 Filter
public class JwtFilter implements Filter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${cookie.name}")
    private String coockieName;

    // header의 jwt가 페이지 이동 시, 유지가 되지 않음..
    // 차선책. 쿠키는 페이지 이동에도 변함없으니 쿠키로 인증 여부를 확인한다.
    private String getCookie(HttpServletRequest request, String key){
        Cookie[] cookies = request.getCookies();
        if(key == null) return null;
        String value ="";
        if(cookies != null){
            for(Cookie ck : cookies){
                if( key.equals(ck.getName()) ){
                    //value = java.net.URLDecoder.decode(ck.getValue(), "UTF-8");   // 인코더를 해야할까?
                    value = ck.getValue();
                    break;
                }
            }
        }
        return value;
    }
    // 실제 필터링 로직은 doFilter 내부에 작성 jwt 토큰의 인증 정보를 SecurityContext에 저장하는 역할
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        // request에서 jwt 토큰 추출(header 미 존재시, 쿠키에서 추출)
        String jwt = "";
        jwt = resolveToken(httpServletRequest) == null ? getCookie(httpServletRequest, coockieName): jwt;

        // token 유효성검증에 통과 후
        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)){
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        }else {
            //log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }

        chain.doFilter(request, response);
    }

    // Request Header에서 토큰 정보를 꺼내오기
    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

