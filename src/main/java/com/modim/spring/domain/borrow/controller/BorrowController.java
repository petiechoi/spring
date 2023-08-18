package com.modim.spring.domain.borrow.controller;

import com.modim.spring.domain.borrow.model.Borrow;
import com.modim.spring.domain.borrow.service.BorrowService;
import com.modim.spring.domain.member.model.Member;
import com.modim.spring.domain.member.util.CurrentMemberUtil;
import com.modim.spring.global.response.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;
    private final CurrentMemberUtil currentMemberUtil;
    // 도서 대여 신청
    @PostMapping("/borrow")
    public ResponseEntity<Response> borrow(@RequestBody HashMap<String, String> map)
    {
        Optional<Member> member = currentMemberUtil.getCurrentMember();
        if(member.isEmpty())
            return new ResponseEntity<>(Response.error("유저를 찾을 수 없습니다."), HttpStatus.OK);

        borrowService.borrowBook(Long.parseLong(map.get("id")));
        return new ResponseEntity<>(Response.success(), HttpStatus.OK);
    }

    // 도서 대여 승인
    @PostMapping("/borrow/apply/{id}")
    public ResponseEntity borrowApply(@PathVariable(value="id")Long id)
    {
        borrowService.borrowApply(id);
        return new ResponseEntity<>(Response.success(),HttpStatus.OK);
    }

    // 도서 승인 삭제
    @DeleteMapping("/borrow/{id}")
    public ResponseEntity borrowDelete(@PathVariable(value="id")Long id)
    {
        borrowService.borrowDelete(id);
        return new ResponseEntity<>(Response.success(),HttpStatus.OK);
    }

    // 도서 대여 목록
    @GetMapping("/borrow")
    public List<Borrow> borrowList()
    {
        return borrowService.borrowList();
    }

}
