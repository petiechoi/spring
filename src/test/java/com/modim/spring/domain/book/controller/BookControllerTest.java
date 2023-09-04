package com.modim.spring.domain.book.controller;

import com.modim.spring.domain.book.dto.BookDto;
import com.modim.spring.domain.book.model.Book;
import com.modim.spring.domain.book.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith({SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void beforeEach(){
        bookRepository.deleteAll();
        String titleori = "테스트 케이스";
        String author = "홍길동";
        String publisher = "모디엠";
        for(int i = 1; i <= 20; i++){
            String title = titleori + String.valueOf(i);
            Book book = Book.builder()
                    .title(title)
                    .author(author)
                    .publisher(publisher).build();
            bookRepository.save(book);
        }
    }

    @Test
    public void book_페이징처리(){
        Pageable pageable =  PageRequest.of(0,3);
        //PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        Page<Book> pages = bookRepository.findAll(pageable);
        System.out.println("전체 페이지수 : " + pages.getTotalPages());
        System.out.println("전체글수 : " + pages.getTotalElements());
        System.out.println("현재 페이지 번호 : " + pages.getNumber());
        System.out.println("페이지당 글 수 : " + pages.getSize());
        System.out.println("================");
        for (Book book : pages.getContent()) {
            System.out.println(book);
        }
    }

    @Test
    void book_등록한다() throws Exception {
        String title = "테스트 도서2";
        String author = "홍길동";
        String publisher = "모디엠";

        BookDto.RequestDto requestDto = BookDto.RequestDto.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .build();

        String url = "http://localhost:" + port + "/api/books";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        Assertions.assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Book> books = bookRepository.findAll();

        Assertions.assertThat(books.get(books.size()-1).getTitle()).isEqualTo(title);
        Assertions.assertThat(books.get(books.size()-1).getAuthor()).isEqualTo(author);
    }
}
