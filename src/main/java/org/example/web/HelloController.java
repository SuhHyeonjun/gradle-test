package org.example.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // JSON을 반환하는 컨트롤러로 만들어 준다.
public class HelloController {

    @GetMapping("/hello") // HTTP Get 요청을 받을 수 있는 API를 만들어 준다.
    public String hello() {
        return "hello";
    }
}
