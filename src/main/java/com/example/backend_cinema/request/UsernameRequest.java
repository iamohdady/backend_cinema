package com.example.backend_cinema.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsernameRequest {

    @JsonProperty("username")
    public String username;
}
