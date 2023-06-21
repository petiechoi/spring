package com.modim.spring.domain.book.model;

import com.modim.spring.domain.book.dto.BookDto.RequestDto;
import com.modim.spring.domain.borrow.model.Borrow;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "borrow_id")
    private Borrow borrow;

    private String title;
    private String author;
    private String publisher;

    @Builder
    public Book(String title, String author, String publisher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }

    
    // 수정
    public void update(RequestDto requestDto){
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.publisher = requestDto.getPublisher();
    }
}
