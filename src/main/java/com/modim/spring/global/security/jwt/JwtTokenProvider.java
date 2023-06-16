package com.modim.spring.global.security.jwt;


import com.modim.spring.domain.member.model.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider implements InitializingBean {
    private final String secret;
    private final Long tokenValidTime;

    private Key key;

    private static final String AUTHORITIES_KEY = "auth";

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidTime) {
        this.secret = secret;
        this.tokenValidTime = tokenValidTime * 1000L;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 매개변수로 전달받은 객체의 권한 정보를 토대로 토큰을 생성한다.
    public String createToken(Authentication authentication){
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidTime);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    // token에 담겨있는 정보를 이용해 Authentication 객체를 리턴하는 메소드 생성
    public Authentication getAuthentication(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // claims과 authorities 정보를 활용해 User (org.springframework.security.core.userdetails.User) 객체 생성
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰의 유효성을 검증
    public boolean validateToken(String token){
        try{
            // 토큰을 파싱해보고 발생하는 exception들을 캐치, 문제가 생기면 false, 정상이면 true를 return
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (SecurityException | MalformedJwtException e){
            // 잘못된 JWT 서명
            //log.info("잘못된 JWT 서명입니다.");
        }catch( ExpiredJwtException e){
            // 만료된 JWT 서명
        }catch(UnsupportedJwtException e){
            // 지원되지 않는 JWT 토큰
        }catch(IllegalArgumentException e){
            // JWT 토큰이 잘못되었다.
        }
        return false;
    }
}
