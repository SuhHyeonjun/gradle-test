package org.example.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.config.auth.LoginUserArgumentResolver;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// LoginUserArgumentResolver가 스프링에서 인식될 수 있도록 WebMvcConfigurer 추가
@RequiredArgsConstructor
@Configurable // 스프링 컨테이너에 수동으로 Bean을 등록
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    // HandlerMethodArgumentResolver는 항상 WebMvcConfigurer의 argumentResolvers를 통해 추가해야 한다.
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }
}
