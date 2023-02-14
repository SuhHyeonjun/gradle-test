package org.example.domain.posts;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// Repository는 인터페이스로 생성한다.
// JpaRepository<Entity 클래스, PK 타입> => 기본적인 CRUD 메소드가 자동 생성
// Entity 클래스와 Entity Repository는 같은 패키지에 위치해야 한다. !
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC") // QueryDSL
    List<Posts> findAllDesc(); // Spring DATA Jpa에서 제공하는 메서드

}
