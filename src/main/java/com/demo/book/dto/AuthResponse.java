package com.demo.book.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AuthResponse {
    private String username;
    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthResponse(String username, String accessToken) {
        this.username = username;
        this.accessToken = accessToken;
    }
}
