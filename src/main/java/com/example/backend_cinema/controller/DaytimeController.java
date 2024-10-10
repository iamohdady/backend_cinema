package com.example.backend_cinema.controller;

import com.example.backend_cinema.mysql.entity.DaytimeEntity;
import com.example.backend_cinema.mysql.entity.UserEntity;
import com.example.backend_cinema.request.AddUserRequest;
import com.example.backend_cinema.request.DaytimeRequest;
import com.example.backend_cinema.request.UsernameRequest;
import com.example.backend_cinema.response.DaytimeResponse;
import com.example.backend_cinema.response.UserResponse;
import com.example.backend_cinema.service.DaytimeService;
import com.example.backend_cinema.service.MovieService;
import com.example.backend_cinema.utils.model.CinemaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard/daytime")
public class DaytimeController {

    private final DaytimeService service;

    @Autowired
    public DaytimeController(DaytimeService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<CinemaResponse<DaytimeResponse>> add(@RequestBody DaytimeRequest request) throws Exception {
        DaytimeResponse response = service.add(request);
        return new ResponseEntity<>(
            new CinemaResponse<>(response),
            HttpStatus.OK
        );
    }

    @PostMapping("/list")
    public ResponseEntity<CinemaResponse<List<DaytimeEntity>>> list() {
        return new ResponseEntity<>(new CinemaResponse<>(service.list()), HttpStatus.OK);
    }

    @PostMapping("/update_daytime")
    public ResponseEntity<CinemaResponse<String>> updateUser(@RequestBody DaytimeRequest request) throws Exception {
        boolean success = service.updateDaytime(request);
        return new ResponseEntity<>(new CinemaResponse<>(success ? "SUCCESS" : "FAIL"), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<CinemaResponse<String>> deleteUser(@RequestBody DaytimeRequest request) throws Exception {
        boolean success = service.deleteDaytime(request.name);
        return new ResponseEntity<>(new CinemaResponse<>(success ? "SUCCESS" : "FAIL"), HttpStatus.OK);
    }
}
