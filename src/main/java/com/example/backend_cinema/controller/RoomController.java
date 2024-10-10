package com.example.backend_cinema.controller;

import com.example.backend_cinema.mysql.entity.MovieEntity;
import com.example.backend_cinema.mysql.entity.RoomEntity;
import com.example.backend_cinema.mysql.entity.SeatEntity;
import com.example.backend_cinema.mysql.entity.UserEntity;
import com.example.backend_cinema.request.*;
import com.example.backend_cinema.response.SeatInRoomResponse;
import com.example.backend_cinema.service.MovieService;
import com.example.backend_cinema.service.RoomService;
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
@RequestMapping("/dashboard/room")
public class RoomController {

    private final RoomService service;

    @Autowired
    public RoomController(RoomService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<CinemaResponse<String>> add(@RequestBody RoomRequest request) throws Exception {
        boolean success = service.add(request);
        return new ResponseEntity<>(new CinemaResponse<>(success ? "SUCCESS" : "FAIL"), HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<CinemaResponse<List<RoomEntity>>> list() {
        return new ResponseEntity<>(new CinemaResponse<>(service.list()), HttpStatus.OK);
    }

    @PostMapping("/detail")
    public ResponseEntity<CinemaResponse<RoomEntity>> getMovieDetails(@RequestBody RoomRequest request) throws Exception {
        RoomEntity room = service.detail(request.name);
        return new ResponseEntity<>(
            new CinemaResponse<>(room),
            HttpStatus.OK
        );
    }

    @PostMapping("/update_status")
    public ResponseEntity<CinemaResponse<String>> updateStatusRoom(@RequestBody RoomRequest request) throws Exception {
        boolean success = service.updateStatus(request);
        return new ResponseEntity<>(new CinemaResponse<>(success ? "SUCCESS" : "FAIL"), HttpStatus.OK);
    }

    @PostMapping("/seats")
    public ResponseEntity<CinemaResponse<List<SeatInRoomResponse>>> getSeatsByRoomName(@RequestBody RoomRequest request) throws Exception {
        List<SeatInRoomResponse> seats = service.getSeatsByRoomName(request.name);
        return new ResponseEntity<>(new CinemaResponse<>(seats), HttpStatus.OK);
    }

    @PostMapping("/available_room/daytime")
    public ResponseEntity<CinemaResponse<List<RoomEntity>>> findAvailableRoomByDayTime(@RequestBody DaytimeRequest request) throws Exception {
        List<RoomEntity> room = service.findAvailableRoomsByDayTime(request.id);
        return new ResponseEntity<>(
            new CinemaResponse<>(room),
            HttpStatus.OK
        );
    }
}
