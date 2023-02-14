package org.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

    // 머스테치 스타터가 컨트롤러에서 문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자를 자동으로 지정해준다.
    // Ex : 앞의 경로는 src/main/resourses/templates로, 뒤의 파일 확장자는 mustache
    // 여기선 retrun "index"를 반환하므로 /index.mustache로 전환되어 View Resolver가 처리한다.
    @GetMapping("/")
    public String index() {
        return "index";
    }
}

