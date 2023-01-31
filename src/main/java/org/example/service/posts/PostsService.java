package org.example.service.posts;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.domain.posts.PostsRepository;
import org.example.web.dto.PostsSaveRequestDto;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // 생성자를 생성하게 하면 Autowired와 동일한 효과를 볼 수 있다.
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
