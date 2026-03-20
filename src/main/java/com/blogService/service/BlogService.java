package com.blogService.service;

import com.blogService.domain.Article;
import com.blogService.dto.AddArticleRequest;
import com.blogService.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    /**
     * 블로그 글 추가
     */
    @Transactional
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

}
