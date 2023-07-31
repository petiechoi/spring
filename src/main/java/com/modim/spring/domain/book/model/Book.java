package com.modim.spring.domain.book.model;

import com.modim.spring.domain.book.dto.BookDto.RequestDto;
import com.modim.spring.domain.borrow.model.Borrow;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="borrow_id")
    private Borrow borrow;

    private String title;
    private String author;
    private String publisher;

    // 수정
    public void update(RequestDto requestDto){
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.publisher = requestDto.getPublisher();
    }

    // 1:1 관계 set
    public void setBorrow(Borrow borrow){
        this.borrow = borrow;
    }
}
