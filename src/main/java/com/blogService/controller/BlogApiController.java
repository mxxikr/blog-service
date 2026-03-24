package com.blogService.controller;

import com.blogService.domain.Article;
import com.blogService.dto.AddArticleRequest;
import com.blogService.dto.ApiResponse;
import com.blogService.dto.ArticleResponse;
import com.blogService.dto.UpdateArticleRequest;
import com.blogService.service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<ApiResponse<ArticleResponse>> addArticle(@RequestBody @Valid AddArticleRequest request, Principal principal) {
        Article savedArticle = blogService.save(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(new ArticleResponse(savedArticle)));
    }

    @GetMapping("/api/articles")
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(articles));
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ApiResponse<ArticleResponse>> findArticle(@PathVariable long id) {
        Article article = blogService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(new ArticleResponse(article)));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteArticle(@PathVariable long id) {
        blogService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<ApiResponse<ArticleResponse>> updateArticle(@PathVariable long id, @RequestBody @Valid UpdateArticleRequest request) {
        Article updateArticle = blogService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(new ArticleResponse(updateArticle)));
    }
}
