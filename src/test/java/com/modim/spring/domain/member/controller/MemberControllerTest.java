package com.modim.spring.domain.member.controller;

import com.modim.spring.domain.member.dto.MemberDto;
import com.modim.spring.domain.member.model.Member;
import com.modim.spring.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberControllerTest {
    @LocalServerPort
    private int port;
    private MockMvc mockMvc;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MemberRepository memberRepository;

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
    void member_등록한다() throws Exception{

        String loginId = "test4";

        MemberDto.RequestDto requestDto = MemberDto.RequestDto.builder()
                        .email("test3@modim.co.kr")
                        .loginId(loginId)
                        .loginPassword("Aheldpa!1")
                        .name("모디엠!1")
                        .build();
        String url = "http://localhost:" + port + "/api/signup";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody())
                .isGreaterThan(0L);

        List<Member> members = memberRepository.findAll();
        assertThat(members.get(members.size()-1).getLoginId()).isEqualTo(loginId);
//        Member member = memberRepository.findById(1L).orElseThrow(() -> new RuntimeException("d"));
//        Assertions.assertEquals(//compare your object and object in database ok?);
    }
}
