package com.example.demo.service;

import com.example.demo.security.JwtTokenUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service encapsulating login and refresh flows.
 */
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUserDetailsService userDetailsService;
    private final JwtTokenUtil tokenUtil;

    public AuthService(AuthenticationManager authenticationManager,
                       AuthUserDetailsService userDetailsService,
                       JwtTokenUtil tokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenUtil = tokenUtil;
    }

    // PUBLIC_INTERFACE
    public Map<String, String> login(String username, String password) {
        var auth = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(auth);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String access = tokenUtil.generateAccessToken(userDetails, Map.of());
        String refresh = tokenUtil.generateRefreshToken(username);
        return Map.of("accessToken", access, "refreshToken", refresh);
    }

    // PUBLIC_INTERFACE
    public Map<String, String> refresh(String refreshToken) {
        var username = tokenUtil.getUsername(refreshToken);
        if (!tokenUtil.isRefreshToken(refreshToken) || tokenUtil.isTokenExpired(refreshToken)) {
            throw new BadCredentialsException("Invalid refresh token");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String access = tokenUtil.generateAccessToken(userDetails, Map.of());
        return Map.of("accessToken", access);
    }
}
