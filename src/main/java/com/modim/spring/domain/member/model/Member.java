package com.modim.spring.domain.member.model;


import com.modim.spring.domain.borrow.model.Borrow;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @Builder.Default
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
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime register_date;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Comment("성별")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Comment("탈퇴여부")
    @Builder.Default
    private boolean deleteYN = false;

    public void setBorrows(Borrow borrow){
        this.borrows.add(borrow);
    }

    public void delBorrow(Borrow borrow){
        this.borrows.remove(borrow);
    }

}
