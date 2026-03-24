package com.blogService.controller;

import com.blogService.dto.AddUserRequest;
import com.blogService.dto.ApiResponse;
import com.blogService.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/signup")
    public ResponseEntity<ApiResponse<Long>> signup(@RequestBody @Valid AddUserRequest request) {
        Long userId = userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(userId));
    }
}
