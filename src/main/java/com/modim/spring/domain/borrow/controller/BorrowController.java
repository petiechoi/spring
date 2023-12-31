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


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BorrowController {
    private final BorrowService borrowService;
    private final CurrentMemberUtil currentMemberUtil;

    // 도서 대여 목록
    @GetMapping("/borrows")
    public List<Borrow> list()
    {
        return borrowService.list();
    }


    // 대여 신청
    @PostMapping("/borrows")
    public ResponseEntity<Response> create(@RequestBody HashMap<String, String> map)
    {
        Optional<Member> member = currentMemberUtil.getCurrentMember();
        if(member.isEmpty())
            return new ResponseEntity<>(Response.error("유저를 찾을 수 없습니다."), HttpStatus.OK);

        borrowService.create(Long.parseLong(map.get("id")));
        return new ResponseEntity<>(Response.success(), HttpStatus.OK);
    }

    // 대여 승인
    @PostMapping("/borrows/{id}")
    public ResponseEntity apply(@PathVariable(value="id")Long id)
    {
        borrowService.apply(id);
        return new ResponseEntity<>(Response.success(),HttpStatus.OK);
    }

    // 대여 삭제(반납)
    @DeleteMapping("/borrows/{id}")
    public ResponseEntity delete(@PathVariable(value="id")Long id)
    {
        borrowService.delete(id);
        return new ResponseEntity<>(Response.success(),HttpStatus.OK);
    }


}
