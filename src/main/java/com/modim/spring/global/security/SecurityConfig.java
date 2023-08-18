package com.modim.spring.global.security;

import com.modim.spring.domain.member.model.Role;
import com.modim.spring.domain.member.service.CustomMemberDetailsService;
import com.modim.spring.global.security.jwt.JwtAccessDeniedHandler;
import com.modim.spring.global.security.jwt.JwtAuthenticationEntryPoint;
import com.modim.spring.global.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //@PreAuthorize, @PostAuthorize 애노테이션을 사용하기 위해 추가
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final JwtFilter jwtFilter;
    private final CustomMemberDetailsService customMemberDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        auth.userDetailsService(customMemberDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()       // token 사용할거기때문에

                // Exception을 핸들링 할때 직접만든 클래스
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)      // 유효한 자격증명을 제공하지 않고 접근하려할때 401 에러
                .accessDeniedHandler(jwtAccessDeniedHandler)        // 필요한 권한이 없이 접근하려 할때 403 에러

                // 세션 미사용
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않음

                // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
                .and()
                .authorizeRequests()
                .antMatchers("/member").hasRole(Role.ROLE_USER.getRole())
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()   // (임시) 나머지 인증필요

                // jwtSecurityConfig 클래스 적용
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
