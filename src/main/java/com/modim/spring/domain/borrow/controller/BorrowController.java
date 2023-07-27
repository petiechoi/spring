package com.modim.spring.domain.borrow.controller;

import com.modim.spring.domain.borrow.model.Borrow;
import com.modim.spring.domain.borrow.service.BorrowService;
import com.modim.spring.global.response.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

    // 도서 대여 신청
    @PostMapping("/borrow")
    public ResponseEntity<Response> borrow(@RequestBody HashMap<String, String> map)
    {
        borrowService.borrowBook(Long.parseLong(map.get("id")));
        return new ResponseEntity<>(Response.success(), HttpStatus.OK);
    }

    // 도서 대여 승인
    @PostMapping("/borrow/apply")
    public Long borrowApply(@PathVariable("id")Long id)
    {
        return borrowService.borrowApply(id);
    }

    // 도서 승인 삭제
    @DeleteMapping("/borrow")
    public void borrowDelete(@PathVariable("id")Long id)
    {
        borrowService.borrowDelete(id);
    }

    // 도서 대여 목록
    @GetMapping("/borrow")
    public List<Borrow> borrowList()
    {
        return borrowService.borrowList();
    }

}
