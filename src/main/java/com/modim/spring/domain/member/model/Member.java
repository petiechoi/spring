package com.modim.spring.domain.member.model;


import com.modim.spring.domain.borrow.model.Borrow;
import lombok.*;
import org.hibernate.annotations.Comment;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Borrow> borrows = new ArrayList<>();

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

}
