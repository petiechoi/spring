package com.modim.spring.domain;

import com.modim.spring.domain.book.model.Book;
import com.modim.spring.domain.book.service.BookService;
import com.modim.spring.domain.member.dto.MemberDto;
import com.modim.spring.domain.member.model.Member;
import com.modim.spring.domain.member.service.AuthService;
import com.modim.spring.domain.member.service.MemberService;
import com.modim.spring.domain.member.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class mainController {

    private final BookService bookService;

    private final MemberService memberService;
    private final AuthService authService;
    private final CurrentMemberUtil currentMemberUtil;
    @GetMapping("/")
    public String root() {return "redirect:/bookList";}

    @GetMapping("/bookList")
    public String bookList(Model model, @RequestParam(value="page", defaultValue = "0")int page){
        Page<Book> bookList = this.bookService.bookList(page);
        model.addAttribute("bookList", bookList);
        return "book/book";
    }

    @GetMapping("/login")
    public String login(Model model){
        return "member/login";
    }


    @GetMapping("/signup")
    public String signup(Model model){
        Member member = currentMemberUtil.getCurrentMember();
        if( !member.equals(null))
            model.addAttribute(member);
        return "redirect:/";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberDto.RequestDto requestDto){
        memberService.signup(requestDto);
        return "redirect:/";
    }
}
