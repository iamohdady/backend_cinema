package com.example.backend_cinema.controller;

import com.example.backend_cinema.mysql.entity.ScheduleEntity;
import com.example.backend_cinema.request.DaytimeRequest;
import com.example.backend_cinema.response.ScheduleRequest;
import com.example.backend_cinema.service.ScheduleResponse;
import com.example.backend_cinema.service.ScheduleService;
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
@RequestMapping("/dashboard/schedule")
public class ScheduleController {

    private final ScheduleService service;

    @Autowired
    public ScheduleController(ScheduleService service) {
        this.service = service;
    }
    @PostMapping("/list")
    public ResponseEntity<CinemaResponse<List<ScheduleEntity>>> list(@RequestBody DaytimeRequest request) throws Exception {
        List<ScheduleEntity> schedules = service.getScheduleByDaytimeName(request.name);
        return new ResponseEntity<>(new CinemaResponse<>(schedules), HttpStatus.OK);
    }

    @PostMapping("/addByDaytimeName")
    public ResponseEntity<CinemaResponse<ScheduleResponse>> addByDaytimeName(@RequestBody ScheduleRequest request) throws Exception {
        ScheduleResponse response = service.addScheduleByDaytimeName(request);
        return new ResponseEntity<>(new CinemaResponse<>(response), HttpStatus.OK);
    }

    @PostMapping("/updateByDaytimeName")
    public ResponseEntity<CinemaResponse<String>> updateByDaytimeName(@RequestBody ScheduleRequest request) throws Exception {
        boolean success = service.updateScheduleByDaytimeName(request);
        return new ResponseEntity<>(new CinemaResponse<>(success ? "SUCCESS" : "FAIL"), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<CinemaResponse<String>> deleteSchedule(@RequestBody String daytimeName) throws Exception {
        boolean success = service.deleteSchedule(daytimeName);
        return new ResponseEntity<>(new CinemaResponse<>(success ? "SUCCESS" : "FAIL"), HttpStatus.OK);
    }
}
