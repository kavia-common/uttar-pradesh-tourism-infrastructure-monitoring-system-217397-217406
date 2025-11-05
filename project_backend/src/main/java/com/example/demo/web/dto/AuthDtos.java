package com.example.demo.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Grouping of DTOs for authentication endpoints.
 */
public class AuthDtos {

    public static class LoginRequest {
        @Schema(description = "Unique username", example = "admin")
        @NotBlank
        public String username;

        @Schema(description = "User password", example = "Admin@123")
        @NotBlank
        public String password;
    }

    public static class LoginResponse {
        @Schema(description = "JWT access token")
        public String accessToken;
        @Schema(description = "Refresh token")
        public String refreshToken;
    }

    public static class RefreshRequest {
        @Schema(description = "Refresh token to exchange for new access token")
        @NotBlank
        public String refreshToken;
    }

    public static class AccessTokenResponse {
        @Schema(description = "Newly issued access token")
        public String accessToken;
    }
}
