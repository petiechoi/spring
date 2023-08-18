package com.modim.spring.domain.member.dto;

import com.modim.spring.domain.member.model.Member;
import com.modim.spring.domain.member.model.Role;
import lombok.*;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
public class MemberDto {

    private MemberDto() throws IllegalArgumentException {
        throw new IllegalArgumentException();
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class RequestDto{
        @NotEmpty(message = "아이디는 필수 입력 값입니다.")
        @Length(min = 4, max = 10, message = "아이디는 4자 이상, 10자 이하로 입력해주십시오.")
        private String loginId;

        @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
        @Length(min = 6, max = 16, message = "비밀번호는 6자 이상, 16자 이하로 입력해주십시오.")
        private String loginPassword;

        @NotEmpty(message = "이름은 필수 입력 값입니다.")
        private String name;

        @NotEmpty(message = "이메일은 필수 입력 값입니다.")
        private String email;

        public Member toEntity(PasswordEncoder passwordEncoder) {
            return Member.builder()
                    .loginId(loginId)
                    .loginPassword(passwordEncoder.encode(loginPassword))
                    .role(Role.ROLE_USER)
                    .email(email)
                    .name(name)
                    .register_date(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class loginDto{
        private String loginId;
        private String loginPassword;
    }

}