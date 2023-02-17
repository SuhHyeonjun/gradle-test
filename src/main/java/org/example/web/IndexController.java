package org.example.web;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.config.auth.LoginUser;
import org.example.config.auth.dto.SessionUser;
import org.example.domain.posts.Posts;
import org.example.service.posts.PostsService;
import org.example.web.dto.PostsResponseDto;
import org.h2.engine.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    // 머스테치 스타터가 컨트롤러에서 문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자를 자동으로 지정해준다.
    // Ex : 앞의 경로는 src/main/resourses/templates로, 뒤의 파일 확장자는 mustache
    // 여기선 retrun "index"를 반환하므로 "/"엔드포인트로 접속시 /index.mustache로 전환되어 View Resolver가 처리한다.
    // Model은 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
    // 여기서는 postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에전달.
    @GetMapping("/") // "@LoginUser SessionUser" user로 어느 컨트롤러든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있도록 추가
    public String index(Model model, @LoginUser SessionUser user) {
        // Model은 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다. 가져온 결과를 posts로 index.mustache에 전달
        model.addAttribute("posts", postsService.findAllDesc());
        /*
        반복 코드 사용 제거를 위해 코드 수정 후, 아래 코드를 주석 처리
        * CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장
        *  SessionUser user = (SessionUser) httpSession.getAttribute("user");
        */
        // 세션에 저장된 값이 있을 때만 model에 userName 등록 (null이면 로그인 버튼이 보임)
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}

