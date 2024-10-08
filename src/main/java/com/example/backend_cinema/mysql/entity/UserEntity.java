package com.example.backend_cinema.mysql.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserEntity {

    public String username;

    public String password;

    public String fullname;

    public String address;

    public String phone;

    public String birthday;

    public String email;

    public String role;

    public String image;

    @JsonProperty("number_code")
    public String numberCode;

    @JsonProperty("status_payment")
    public String statusPayment;

    public String amount;

    public String status;

    @JsonProperty("last_login")
    public String lastLogin;

    @JsonProperty("access_token")
    public String accessToken;

    @JsonProperty("refresh_token")
    public String refreshToken;
}
