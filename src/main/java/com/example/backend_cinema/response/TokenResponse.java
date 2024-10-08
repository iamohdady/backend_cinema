package com.example.backend_cinema.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse {
    @JsonProperty("access_token")
    public final String accessToken;

    @JsonProperty("refresh_token")
    public final String refreshToken;

    public TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}