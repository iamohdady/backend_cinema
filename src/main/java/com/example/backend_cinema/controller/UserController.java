package com.example.backend_cinema.controller;

import com.example.backend_cinema.mysql.entity.UserEntity;
import com.example.backend_cinema.request.*;
import com.example.backend_cinema.response.CountResponse;
import com.example.backend_cinema.response.ForgetPasswordResponse;
import com.example.backend_cinema.response.TokenResponse;
import com.example.backend_cinema.response.UserResponse;
import com.example.backend_cinema.service.UserService;
import com.example.backend_cinema.utils.model.CinemaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard/user")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/list")
    public ResponseEntity<CinemaResponse<List<UserEntity>>> list() {
        return new ResponseEntity<>(new CinemaResponse<>(service.list()), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<CinemaResponse<TokenResponse>> login(@RequestBody LoginRequest request) throws Exception {
        TokenResponse tokensResponse = service.login(request.username, request.password);
        return new ResponseEntity<>(
            new CinemaResponse<>(tokensResponse),
            HttpStatus.OK
        );
    }

    @PostMapping("/add")
    public ResponseEntity<CinemaResponse<UserResponse>> add(@RequestBody AddUserRequest request) throws Exception {
        UserResponse response = service.add(request);
        return new ResponseEntity<>(
            new CinemaResponse<>(response),
            HttpStatus.OK
        );
    }

    @PostMapping("/update_password")
    public ResponseEntity<CinemaResponse<String>> updatePassword(@RequestBody UpdatePasswordRequest request) throws Exception {
        return new ResponseEntity<>(
            new CinemaResponse<>(
                service.updateUserPassword(request) ? "SUCCESS" : "FAIL"
            ),
            HttpStatus.OK
        );
    }

    @PostMapping("/update_role")
    public ResponseEntity<CinemaResponse<String>> updateRole(@RequestBody UpdateRoleRequest request) throws Exception {
        return new ResponseEntity<>(
            new CinemaResponse<>(
                service.updateRole(request.username, request.role) ? "SUCCESS" : "FAIL"
            ),
            HttpStatus.OK
        );
    }

    @PostMapping("/forget_password")
    public ResponseEntity<CinemaResponse<ForgetPasswordResponse>> forgetPassword(@RequestBody ForgetPasswordRequest request) throws Exception {
        ForgetPasswordResponse newPassword = service.forget(request.username);
        return new ResponseEntity<>(
                new CinemaResponse<>(newPassword),
                HttpStatus.OK
        );
    }

    @PostMapping("/detail")
    public ResponseEntity<CinemaResponse<UserEntity>> getMemberDetails(@RequestBody UsernameRequest request) throws Exception {
        UserEntity member = service.getMemberDetails(request.username);
        return new ResponseEntity<>(
            new CinemaResponse<>(member),
            HttpStatus.OK
        );
    }

    @PostMapping("/update_user")
    public ResponseEntity<CinemaResponse<String>> updateUser(@RequestBody AddUserRequest request) throws Exception {
        boolean success = service.updateMember(request);
        return new ResponseEntity<>(new CinemaResponse<>(success ? "SUCCESS" : "FAIL"), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<CinemaResponse<String>> deleteUser(@RequestBody UsernameRequest request) throws Exception {
        boolean success = service.deleteUser(request.username);
        return new ResponseEntity<>(new CinemaResponse<>(success ? "SUCCESS" : "FAIL"), HttpStatus.OK);
    }
    @PostMapping("/count")
    public ResponseEntity<CinemaResponse<CountResponse>> countMovie() throws Exception {
        CountResponse countResponse = service.countMember();
        return new ResponseEntity<>(new CinemaResponse<>(countResponse), HttpStatus.OK);
    }
}