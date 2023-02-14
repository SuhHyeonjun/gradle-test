package org.example.web;

import lombok.RequiredArgsConstructor;
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

    // 머스테치 스타터가 컨트롤러에서 문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자를 자동으로 지정해준다.
    // Ex : 앞의 경로는 src/main/resourses/templates로, 뒤의 파일 확장자는 mustache
    // 여기선 retrun "index"를 반환하므로 "/"엔드포인트로 접속시 /index.mustache로 전환되어 View Resolver가 처리한다.
    // Model은 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
    // 여기서는 postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에전달.
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
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

