package com.blogService.service;

import com.blogService.config.error.exception.ArticleNotFoundException;
import com.blogService.domain.Article;
import com.blogService.domain.Comment;
import com.blogService.dto.AddArticleRequest;
import com.blogService.dto.AddCommentRequest;
import com.blogService.dto.UpdateArticleRequest;
import com.blogService.repository.BlogRepository;
import com.blogService.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;

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
    @Transactional(readOnly = true)
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    /**
     * 블로그 글 조회
     */
    @Transactional(readOnly = true)
    public Article findById(Long id) {
        return blogRepository.findById(id).orElseThrow(ArticleNotFoundException::new);
    }

    /**
     * 블로그 글 삭제
     */
    @Transactional
    public void delete(Long id) {
        Article article = blogRepository.findById(id).orElseThrow(ArticleNotFoundException::new);
        authorizeArticleAuthor(article);
        blogRepository.deleteById(id);
    }

    /**
     * 블로그 글 수정
     */
    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id).orElseThrow(ArticleNotFoundException::new);

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    /**
     * 댓글 추가
     */
    @Transactional
    public Comment addComment(AddCommentRequest request, String userName) {
        Article article = blogRepository.findById(request.getArticleId()).orElseThrow(ArticleNotFoundException::new);
        return commentRepository.save(request.toEntity(userName, article));
    }

    // 게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
