package com.modim.spring.domain.book.controller;

import com.modim.spring.domain.book.dto.BookDto;
import com.modim.spring.domain.book.model.Book;
import com.modim.spring.domain.book.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
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

        String url = "http://localhost:" + port + "/api/book";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        Assertions.assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Book> books = bookRepository.findAll();

        Assertions.assertThat(books.get(books.size()-1).getTitle()).isEqualTo(title);
        Assertions.assertThat(books.get(books.size()-1).getAuthor()).isEqualTo(author);
    }
}
