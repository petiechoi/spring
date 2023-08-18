package com.modim.spring.domain.book.controller;

import com.modim.spring.domain.book.dto.BookDto.RequestDto;
import com.modim.spring.domain.book.model.Book;
import com.modim.spring.domain.book.service.BookService;
import com.modim.spring.global.response.dto.Response;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity bookCreate(@Valid @RequestBody RequestDto requestDto) {
        if( bookService.bookCreate(requestDto) > 0L )
            return new ResponseEntity<>(Response.success(),HttpStatus.OK);
        else return new ResponseEntity<>(Response.error("다시시도하여 주십시오."),HttpStatus.OK);
    }

    // 도서 삭제
    @DeleteMapping("/book/{id}")
    public ResponseEntity bookDelete(@PathVariable("id") Long id){
        bookService.bookDelete(id);
        return new ResponseEntity<>(Response.success(),HttpStatus.OK);
    }

    // 도서 수정
    @PutMapping("/book/{id}")
    public ResponseEntity bookUpdate(@Valid @RequestBody RequestDto requestDto, @PathVariable(value="id") Long id) {
        if (id == bookService.bookUpdate(requestDto, id))
            return new ResponseEntity<>(Response.success(), HttpStatus.OK);
        return new ResponseEntity<>(Response.error("대상을 찾을 수 없습니다."),HttpStatus.OK);
    }

    // 도서 단일 조회
    @GetMapping("/book/{id}")
    public Book bookView(@PathVariable Long id){
        return bookService.bookView(id);
    }
}
