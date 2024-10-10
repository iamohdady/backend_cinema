package com.example.backend_cinema.controller;

import com.example.backend_cinema.mysql.entity.ShowtimeEntity;
import com.example.backend_cinema.mysql.entity.UserEntity;
import com.example.backend_cinema.request.AddUserRequest;
import com.example.backend_cinema.request.CaculateEndtimeRequest;
import com.example.backend_cinema.request.ShowtimeRequest;
import com.example.backend_cinema.request.UsernameRequest;
import com.example.backend_cinema.response.DetailShowtimeResponse;
import com.example.backend_cinema.response.ShowtimeResponse;
import com.example.backend_cinema.response.UserResponse;
import com.example.backend_cinema.service.ShowtimeService;
import com.example.backend_cinema.utils.model.CinemaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/dashboard/showtime")
public class ShowtimeController {

    private final ShowtimeService service;

    @Autowired
    public ShowtimeController(ShowtimeService service) {
        this.service = service;
    }

    @PostMapping("/list")
    public ResponseEntity<CinemaResponse<List<ShowtimeEntity>>> list() throws Exception {
        List<ShowtimeEntity> response = service.list();
        return new ResponseEntity<>(
            new CinemaResponse<>(response),
            HttpStatus.OK
        );
    }

    @PostMapping("/add")
    public ResponseEntity<CinemaResponse<ShowtimeEntity>> add(@RequestBody ShowtimeRequest request) throws Exception {
        ShowtimeEntity response = service.add(request);
        return new ResponseEntity<>(
            new CinemaResponse<>(response),
            HttpStatus.OK
        );
    }

    @PostMapping("/detail")
    public ResponseEntity<CinemaResponse<ShowtimeEntity>> getMemberDetails(@RequestBody ShowtimeRequest request) throws Exception {
        ShowtimeEntity member = service.getShowtimeDetails(request.id);
        return new ResponseEntity<>(
            new CinemaResponse<>(member),
            HttpStatus.OK
        );
    }

    @PostMapping("/calculate/endtime")
    public ResponseEntity<CinemaResponse<String>> getMemberDetails(@RequestBody CaculateEndtimeRequest request) throws Exception {
        String endTime = service.calculateEndTime(request.movieId, request.startTime);
        return new ResponseEntity<>(
            new CinemaResponse<>(endTime),
            HttpStatus.OK
        );
    }
}
