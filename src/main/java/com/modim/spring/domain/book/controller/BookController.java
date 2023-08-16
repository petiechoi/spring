package com.modim.spring.domain.book.controller;

import com.modim.spring.domain.book.dto.BookDto.RequestDto;
import com.modim.spring.domain.book.model.Book;
import com.modim.spring.domain.book.service.BookService;
import com.modim.spring.global.response.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    // 도서 등록
    @PostMapping("/book")
    public Long bookCreate(@Valid @RequestBody RequestDto requestDto) {
        return bookService.bookCreate(requestDto);
    }

    // 도서 삭제
    @DeleteMapping("/book")
    public void bookDelete(@PathVariable("id") Long id){
        bookService.bookDelete(id);
    }

    // 도서 수정
    @PutMapping("/book/{id}")
    public ResponseEntity bookUpdate(@Valid @RequestBody RequestDto requestDto, @PathVariable(value="id") Long id) {
        if (id == bookService.bookUpdate(requestDto, id))
            return new ResponseEntity<>(Response.success(), HttpStatus.OK);
        return new ResponseEntity<>(Response.error("대상을 찾을 수 없습니다."),HttpStatus.OK);
    }

    // 도서 전체 목록 조회(페이징)
    @GetMapping("/bookList")
    public ResponseEntity bookList(final Pageable pageable){
        Page<Book> books = bookService.bookList(0);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // 도서 단일 조회
    @GetMapping("/book/{id}")
    public Book bookView(@PathVariable Long id){
        return bookService.bookView(id);
    }
}
