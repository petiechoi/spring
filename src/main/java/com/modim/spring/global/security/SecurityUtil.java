package com.modim.spring.global.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtil {

    public static Optional<String> getCurrentloginId() {
        // Security Context에 Authentication 객체가 저장되는 시점은
        // JwtFilter의 doFilter메소드에서 Request가 들어올 때 SecurityContext에 Authentication 객체를 저장해서 사용

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null){
            //log.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String username = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            username = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(username);
    }
}
