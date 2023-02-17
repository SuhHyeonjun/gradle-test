package org.example.web;

import org.example.config.auth.SecurityConfig;
import org.example.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 스프링 부트 테스트와 JUnit 사이에 연결자 역할
@RunWith(SpringRunner.class)
// Web에 집중할 수 있는 어노테이션
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = SecurityConfig.class)}) // 스캔 대상에서 SecurityConfig를 제거
public class HelloControllerTest {

    @Autowired // 스프링이 관리하는 Bean을 주입 받는다.
    private MockMvc mvc; // 웹 API 테스트할 때 사용 (GET, POST 등)

    @WithMockUser(roles = "USER")
    @Test
    public void hello_return() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) // /hello 주소로 get 요청
                .andExpect(status().isOk()) // HTTP Header의 상태 검증
                .andExpect(content().string(hello)); // 응답 본문의 내용 검증
    }

    @WithMockUser(roles = "USER")
    @Test
    public void helloDto_return() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                // param : API 테스트할 때 사용될 요청 파라미터 설정 (단, String만 허용)
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                // jsonPath : JSON응답값을 필드별로 검증 ($를 기준으로 필드명 명시)
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
