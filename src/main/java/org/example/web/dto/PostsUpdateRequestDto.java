package org.example.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Update는 쿼리를 날릴필요없이 Entity 객체의 값만 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경사항을 반영한다.
    // PostsService의 update() 메소드를 확인
}
