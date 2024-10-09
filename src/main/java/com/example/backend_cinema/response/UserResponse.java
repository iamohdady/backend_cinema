package com.example.backend_cinema.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse {

    @JsonProperty("result")
    public String result;

    @JsonProperty("username")
    public String username;

    @JsonProperty("password")
    public String password;

    public UserResponse(String result, String username, String password) {
        this.result = result;
        this.username = username;
        this.password = password;
    }
}