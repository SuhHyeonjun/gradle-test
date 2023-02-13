package org.example.domain.posts;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) // 스프링 부트 테스트와 JUnit 사이에 연결자 역할
@SpringBootTest // H2 데이터 베이스를 자동 실행해 준다.
public class PostsRepositoryTest {

    @Autowired // 스프링이 관리하는 Bean을 주입 받는다.
    PostsRepository postsRepository;

    @After // 단위 테스트가 끝날 때 마다 수행되는 메소드 지정
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        // 테이블 posts에 insert/update 쿼리를 실행
        // id값이 있다면 update, 없다면 insert 실행
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("study@gmail.com")
                .build());

        // when
        // findAll() : 테이블 posts에 있는 모든 데이터를 조회해오는 메소드
        List<Posts> postsLists = postsRepository.findAll();

        // then
        Posts posts = postsLists.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        // given
        LocalDateTime date = LocalDateTime.of(2023,2,1,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("cotent")
                .author("author")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>>>> createDate : " + posts.getCreatedDate() + ", modifiedDate : " + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(date); // posts.getCreatedDate()이 date 값 보다 이후 시간인지 비교
        assertThat(posts.getModifiedDate()).isAfter(date);
    }
}
