package com.modim.spring.domain.borrow.model;

import com.modim.spring.domain.book.model.Book;
import com.modim.spring.domain.member.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="borrow_id")
    private Long id;

    //@ManyToOne(fetch = FetchType.LAZY) // done ?
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "borrow")
    private Book book;

    @Column(name="borrow_date")
    private String date;

    @Column(name="status")
    @Enumerated(EnumType.STRING) // 이거안해주면 값 이상하게 들어감
//    @Builder.Default
    private Status status = Status.POSSIBLE; // 이렇게하면 빌더할때 null넣으면 어플라이로 들어감  ㅅㄱ

    @Builder
    public Borrow(Member member, Book book) {
        this.member = member;
        this.book = book;
        this.date = LocalDateTime.now().toString();
        this.status = Status.APPLY;
    }

    public void Apply() {
        if (this.status.equals(Status.APPLY)) {
            this.status = Status.PROGRESS;
            this.date = LocalDateTime.now().toString();
        }
        else throw new RuntimeException("알 수 없는 오류로 승인할 수 없습니다.");
    }

    public void setBook(Book book){
        this.book = book;
    }

}
