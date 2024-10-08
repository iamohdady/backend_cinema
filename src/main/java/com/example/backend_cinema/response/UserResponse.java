package com.example.backend_cinema.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse {

    @JsonProperty("result")
    public String result;

    @JsonProperty("password")
    public String password;

    public UserResponse(String result, String password) {
        this.result = result;
        this.password = password;
    }
}