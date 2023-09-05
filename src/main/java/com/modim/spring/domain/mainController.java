package com.modim.spring.domain;

import com.modim.spring.domain.book.model.Book;
import com.modim.spring.domain.book.service.BookService;
import com.modim.spring.domain.file.service.FileService;
import com.modim.spring.domain.member.service.MemberService;
import com.modim.spring.domain.member.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class mainController {
    private final BookService bookService;
    private final FileService fileService;
    private final CurrentMemberUtil currentMemberUtil;

    @Value("${cookie.name}")
    private String coockieName;

    @GetMapping("/")
    public String root() {return "redirect:/books";}

    // 도서목록
    @GetMapping("/books")
    public String books(Model model, @RequestParam(value="page", defaultValue = "0")int page){
        Page<Book> books = bookService.list(page);
        model.addAttribute("books", books);
        return "pages/books";
    }

    // 사내문서
    @GetMapping("/downloads")
    public String downloadList(Model model){
        model.addAttribute("files",fileService.list());
        return "pages/downloads";
    }

    // 로그인
    @GetMapping("/login")
    public String login(Model model){
        return "pages/login";
    }

    // 로그아웃
    @GetMapping("/leave")
    public String leave(@CookieValue(name="modim", required = false)String coockieValue, HttpServletResponse response){
        if(coockieValue.length() > 0){
            Cookie cookie = new Cookie(coockieName, null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return "redirect:/";
    }

    // 회원가입
    @GetMapping("/join")
    public String join(Model model){
        if(currentMemberUtil.getCurrentMember().isEmpty())
            return "pages/join";
        return "redirect:/";
    }
}
