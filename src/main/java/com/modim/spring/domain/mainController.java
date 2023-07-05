package com.modim.spring.domain;

import com.modim.spring.domain.book.model.Book;
import com.modim.spring.domain.book.service.BookService;
import com.modim.spring.domain.member.dto.MemberDto;
import com.modim.spring.domain.member.model.Member;
import com.modim.spring.domain.member.model.TokenResponseDto;
import com.modim.spring.domain.member.service.AuthService;
import com.modim.spring.domain.member.service.MemberService;
import com.modim.spring.domain.member.util.CurrentMemberUtil;
import com.modim.spring.global.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class mainController {

    private final BookService bookService;

    private final MemberService memberService;
    private final AuthService authService;
    private final CurrentMemberUtil currentMemberUtil;

    @Value("${cookie.name}")
    private String coockieName;

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

    @PostMapping("/login")
    public String authorize(@Valid MemberDto.loginDto loginDto, HttpServletResponse response){
        TokenResponseDto tokenResponseDto = authService.login(loginDto);
        response.addCookie(setCookie(tokenResponseDto.getToken()));
        response.setHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenResponseDto.getToken());

//        HttpHeaders httpHeaders = new HttpHeaders(); 와 너무 느린거 아니냐 ;;
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenResponseDto.getToken());
        // return ResponseEntity.ok(tokenResponseDto);
        return "redirect:/signup";
    }

    public Cookie setCookie(String coockieValue)
    {
        Cookie cookie = new Cookie(coockieName, coockieValue);
        cookie.setMaxAge(60 * 60 * 24 * 30);    // 30 days
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

    @GetMapping("signup")
    public String signup(Model model, @AuthenticationPrincipal UserDetails userDetails){
        System.out.println("userDetails = " + userDetails);
        if (userDetails != null) {
            return "signup";
        }
        return "/";
    }

//    @GetMapping("/signup")
//    public String signup(Model model){
//        Member member = currentMemberUtil.getCurrentMember();
//        if( !member.equals(null))
//            model.addAttribute(member);
//        return "redirect:/";
//    }

    @PostMapping("/signup")
    public String signup(@Valid MemberDto.RequestDto requestDto){
        memberService.signup(requestDto);
        return "redirect:/";
    }
}
