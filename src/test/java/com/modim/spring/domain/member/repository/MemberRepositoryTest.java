package com.modim.spring.domain.member.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modim.spring.domain.member.model.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest
class MemberRepositoryTest {
    private MockMvc mockMvc;
    @Autowired MemberRepository memberRepository;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void gabojago() throws Exception {
        this.mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("hello"));
    }

    @Test
    void repositoryTestGaboja(){
        memberRepository.save(
                //create member
                Member.builder()
                        .build()
        );
        Member member = memberRepository.findById(1L).orElseThrow(() -> new RuntimeException("fuck you"));
        //Assertions.assertEquals(//compare your object and object in database ok?);
    }


}
