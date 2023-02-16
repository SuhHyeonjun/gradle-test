package org.example.config.auth;

import java.util.Collections;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.config.auth.dto.OAuthAttributes;
import org.example.config.auth.dto.SessionUser;
import org.example.domain.user.User;
import org.example.domain.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 현재 로그인 진행 중인 서비스를 구분하는 코드 (네이버 로그인인지 구글 로그인인지 구분)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth2 로그인 진행 시 키가 되는 필드 값 (Primary Key와 같은 의미)을 의미
        // 구글의 기본 코드는 "sub", 후에 네이버 로그인과 구글 로그인을 동시 지원할 때 사용
        String userNameAttributeName = userRequest.
                getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService를 통해 가져온 OAuthUser의 attribute를 담을 클래스 ( 네이버 등 다른 소셜 로그인도 이 클래스 사용)
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        // User 클래스를 사용하지 않고 SessionUser클래스를 사용하는 이유는 오류 방지.
        httpSession.setAttribute("user", new SessionUser(user)); // SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto 클래스

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    // 사용자 정보가 변경 될시 User 엔티티에도 반영
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
