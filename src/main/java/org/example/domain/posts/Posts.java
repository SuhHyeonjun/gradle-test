package org.example.domain.posts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Getter // 클래스 내 모든 필드의 Getter 자동 생성
@NoArgsConstructor // 기본 생성자 자동 추가 = public Posts() {}
@Entity // 테이블과 링크될 클래스임을 나타냄 (Jpa)
// => Entity 클래스에선 절대 Setter를 만들지 않는다. 정말 필요하다면 목적과 의도를 나타낼 수 있는 메소드를 따로 추가한다.
public class Posts {

    @Id // 해당 테이블의 pk(기본키)필드를 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성 규칙
    private Long id;

    @Column(length = 500, nullable = false) // 테이블의 column을 나타냄
    private String title;

    @Column(columnDefinition = "Text", nullable = false)
    private String content;

    private String author;

    @Builder // 해당 클래스의 빌더 패턴 클래스를 생성
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
