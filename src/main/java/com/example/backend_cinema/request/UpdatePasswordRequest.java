package com.example.backend_cinema.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.StringUtils;

public class UpdatePasswordRequest {

    @JsonProperty("username")
    public String username;

    @JsonProperty("old_password")
    public String oldPassword;

    @JsonProperty("new_password")
    public String newPassword;

    @JsonProperty("confirm_new_password")
    public String confirmPassword;

    public boolean isValid() {
        return StringUtils.hasLength(username)
            && StringUtils.hasLength(oldPassword)
            && StringUtils.hasLength(newPassword)
            && StringUtils.hasLength(confirmPassword)
            && !oldPassword.equals(newPassword);
    }

//    public boolean isWeakNewPassword() {
//        return !newPassword.matches("^(?=.*[!@#$%^&*(),.?\":{}|<>])(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$");
//    }
}
