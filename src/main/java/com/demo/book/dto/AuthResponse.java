package com.demo.book.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AuthResponse {
    private String username;
    private String accessToken;
    private String tokenType = "Bearer ";
    private String refreshToken;
    public AuthResponse(String username, String accessToken,String refreshToken) {
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
