package com.example.backend_cinema.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateRoleRequest {

    @JsonProperty("username")
    public String username;

    @JsonProperty("role")
    public String role;
}
