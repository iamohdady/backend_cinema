package com.example.backend_cinema.controller;

import com.example.backend_cinema.mysql.entity.UserEntity;
import com.example.backend_cinema.request.AddUserRequest;
import com.example.backend_cinema.request.LoginRequest;
import com.example.backend_cinema.request.UpdatePasswordRequest;
import com.example.backend_cinema.request.UpdateRoleRequest;
import com.example.backend_cinema.response.TokenResponse;
import com.example.backend_cinema.response.UserResponse;
import com.example.backend_cinema.service.UserService;
import com.example.backend_cinema.utils.crypt.CryptUtils;
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

    @PostMapping("update_role")
    public ResponseEntity<CinemaResponse<String>> updateRole(@RequestBody UpdateRoleRequest request) throws Exception {
        return new ResponseEntity<>(
            new CinemaResponse<>(
                service.updateRole(request.username, request.role) ? "SUCCESS" : "FAIL"
            ),
            HttpStatus.OK
        );
    }
}