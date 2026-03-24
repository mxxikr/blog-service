package com.blogService.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_ERROR = "error";

    private final String status;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(STATUS_SUCCESS, null, data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(STATUS_SUCCESS, message, data);
    }

    public static ApiResponse<Void> error(String message) {
        return new ApiResponse<>(STATUS_ERROR, message, null);
    }
}
