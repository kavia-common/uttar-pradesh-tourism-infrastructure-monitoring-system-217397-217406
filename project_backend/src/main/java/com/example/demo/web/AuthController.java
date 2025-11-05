package com.example.demo.web;

import com.example.demo.service.AuthService;
import com.example.demo.web.dto.AuthDtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication endpoints for login and token refresh.
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "JWT authentication and token refresh")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) { this.authService = authService; }

    // PUBLIC_INTERFACE
    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticate with username and password and receive access and refresh tokens.")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        var tokens = authService.login(request.username, request.password);
        var resp = new LoginResponse();
        resp.accessToken = tokens.get("accessToken");
        resp.refreshToken = tokens.get("refreshToken");
        return ResponseEntity.ok(resp);
    }

    // PUBLIC_INTERFACE
    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token", description = "Exchange a valid refresh token for a new access token.")
    public ResponseEntity<AccessTokenResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        var token = authService.refresh(request.refreshToken);
        var resp = new AccessTokenResponse();
        resp.accessToken = token.get("accessToken");
        return ResponseEntity.ok(resp);
    }
}
