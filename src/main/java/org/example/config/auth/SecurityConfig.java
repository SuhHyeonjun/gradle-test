package org.example.config.auth;

import lombok.RequiredArgsConstructor;
import org.example.domain.user.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Securiy 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // h2-console 화면을 사용하기 위해 해당 옵션을 disable
        http.csrf().disable().headers().frameOptions().disable()
                .and()// URL 별 권환 관리 설정 (authorizeRequests()가 선언되어야만 anyMatchers옵션 사용가능)
                    .authorizeRequests()
                    // antMatchers를 통해 권환 관리 대상을 지정하고, URL,HTTP 메소드별 관리 가능
                    .antMatchers("/", "/css/**", "/images/**", "/js**", "/h2-console/**", "/profile").permitAll() // "/"등 지정된 URL들은 permitAll() 옵션으로 전체 열람 권한 부여
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // 해당 주소는 USER 권한을 가진 사람만 가능
                    .anyRequest().authenticated() // 설정된 값들 이외 나머지 URL들은 모두 인증된 사용자(로그인한)들에게만 허용
                .and()
                    .logout().logoutSuccessUrl("/") // 로그아웃 성공시 해당 주소로 이동
                .and()
                    .oauth2Login() // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                    .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정 담당
                    .userService(customOAuth2UserService) // 소셜 로그인 성공 시 후속 조치를 진행할 userService 인터페이스의 구현체 등록
                    // 리소스 서버(소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시 가능.
                .and().defaultSuccessUrl("/", true);
    }
}
