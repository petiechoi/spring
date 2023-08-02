package com.modim.spring.domain.borrow.model;

import com.modim.spring.domain.book.model.Book;
import com.modim.spring.domain.member.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "borrow")
    private Book book;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime borrowedAt;

    @Column(name="status")
    @Enumerated(EnumType.STRING) // 이거안해주면 값 이상하게 들어감
//    @Builder.Default
    private Status status;

    @Builder
    public Borrow(Member member, Book book) {
        this.member = member;
        this.book = book;
        this.borrowedAt = LocalDateTime.now();
        this.status = Status.APPLY;
    }

    public void Apply() {
        if (this.status.equals(Status.APPLY)) {
            this.status = Status.PROGRESS;
            this.borrowedAt = LocalDateTime.now();
        }
        else throw new RuntimeException("알 수 없는 오류로 승인할 수 없습니다.");
    }

    public void setBook(Book book){
        this.book = book;
    }

}
