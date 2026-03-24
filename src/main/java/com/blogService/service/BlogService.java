package com.blogService.service;

import com.blogService.domain.Article;
import com.blogService.dto.AddArticleRequest;
import com.blogService.dto.UpdateArticleRequest;
import com.blogService.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    /**
     * 블로그 글 추가
     */
    @Transactional
    public Article save(AddArticleRequest request, String userName) {
        return blogRepository.save(request.toEntity(userName));
    }

    /**
     * 블로그의 모든 글 조회
     */
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    /**
     * 블로그 글 조회
     */
    public Article findById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    /**
     * 블로그 글 삭제
     */
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    /**
     * 블로그 글 수정
     */
    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
