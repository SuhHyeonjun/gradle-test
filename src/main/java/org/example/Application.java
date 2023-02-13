package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication // 스프링 부트의 자동 설정 (항상 프로젝트 최상단)
public class Application {
    public static void main(String[] args) {
        // SpringApplication.run() 메서드로 내부 WAS를 실행
        SpringApplication.run(Application.class, args);
    }
}