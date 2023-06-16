package com.modim.spring.domain.member.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @Comment("로그인 아이디")
    private String loginId;

    @Comment("비밀번호")
    private String loginPassword;

    @Comment("이름")
    private String name;

    @Comment("이메일")
    private String email;

    @Comment("가입날짜")
    private String register_date;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String loginId, String loginPassword, String name, String email, Role role) {
        this.loginId = loginId;
        this.loginPassword = loginPassword;
        this.name = name;
        this.email = email;
        this.register_date = LocalDateTime.now().toString();
        this.role = role;
    }
}
