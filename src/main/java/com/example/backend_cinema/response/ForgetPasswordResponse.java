package com.example.backend_cinema.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ForgetPasswordResponse {

    @JsonProperty("result")
    public String result;

    @JsonProperty("new_password")
    public String newPassword;

    public ForgetPasswordResponse(String result, String newPassword) {
        this.result = result;
        this.newPassword = newPassword;
    }
}
