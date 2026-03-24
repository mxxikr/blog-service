package com.blogService.controller;

import com.blogService.dto.ApiResponse;
import com.blogService.dto.CreateAccessTokenRequest;
import com.blogService.dto.CreateAccessTokenResponse;
import com.blogService.service.RefreshTokenService;
import com.blogService.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/api/token")
    public ResponseEntity<ApiResponse<CreateAccessTokenResponse>> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(new CreateAccessTokenResponse(newAccessToken)));
    }

    @DeleteMapping("/api/refresh-token")
    public ResponseEntity<ApiResponse<Void>> deleteRefreshToken() {
        refreshTokenService.delete();

        return ResponseEntity.ok()
                .body(ApiResponse.success(null));
    }
}
