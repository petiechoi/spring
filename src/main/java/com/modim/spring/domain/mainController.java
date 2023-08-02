package com.modim.spring.domain;

import com.modim.spring.domain.book.model.Book;
import com.modim.spring.domain.book.service.BookService;
import com.modim.spring.domain.borrow.model.Borrow;
import com.modim.spring.domain.borrow.service.BorrowService;
import com.modim.spring.domain.member.dto.MemberDto;
import com.modim.spring.domain.member.model.Member;
import com.modim.spring.domain.member.model.TokenResponseDto;
import com.modim.spring.domain.member.service.AuthService;
import com.modim.spring.domain.member.service.MemberService;
import com.modim.spring.domain.member.util.CurrentMemberUtil;
import com.modim.spring.global.security.jwt.JwtFilter;
import com.modim.spring.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class mainController {

    private final BookService bookService;
    private final MemberService memberService;
    private final BorrowService borrowService;
    private final JwtTokenProvider jwtTokenProvider;
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

    @GetMapping("/signup")
    public String signup(Model model, @CookieValue(name="modim", required = false)String modim){
        if(modim == null)
        {
            return "login";
        }
        if( jwtTokenProvider.validateToken(modim) ){
            currentMemberUtil.getCurrentMember();
            String id;
            Object principal = jwtTokenProvider.getAuthentication(modim).getPrincipal();
            if( principal instanceof UserDetails ){
                id = ((UserDetails)principal).getUsername();
            } else {
                id = principal.toString();
            }
            return "redirect:/bookList";
        }
        return "redirect:/";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberDto.RequestDto requestDto){
        memberService.signup(requestDto);
        return "redirect:/";
    }

    @GetMapping("/leave")
    public String logout(@CookieValue(name="modim", required = false)String coockieValue, HttpServletResponse response){
        if(coockieValue.length() > 0){
            Cookie cookie = new Cookie(coockieName, null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return "redirect:/bookList";
    }

    @GetMapping("/bookmng")
    public String bookmng(Model model){
        List<Borrow> borrowList = borrowService.borrowList();
        model.addAttribute("borrowList",borrowList);
        return "admin/bookmng";
    }

}
