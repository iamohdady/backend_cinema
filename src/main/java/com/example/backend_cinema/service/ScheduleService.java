package com.example.backend_cinema.service;

import com.example.backend_cinema.CinemaSql;
import com.example.backend_cinema.mysql.MySqlConnector;
import com.example.backend_cinema.mysql.entity.ScheduleEntity;
import com.example.backend_cinema.request.DaytimeRequest;
import com.example.backend_cinema.response.ScheduleRequest;
import com.example.backend_cinema.utils.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ScheduleService {

    private final MySqlConnector mysql;

    @Autowired
    public ScheduleService(MySqlConnector mysql) {
        this.mysql = mysql;
    }

    public List<ScheduleEntity> getScheduleByDaytimeName(String name) throws Exception {
        if (StringUtils.hasLength(name)) {
            return mysql.selectList(CinemaSql.selectScheduleByDaytimeName(name), ScheduleEntity.class);
        }
        throw new BadRequestException("Daytime name is empty");
    }

    public ScheduleResponse addScheduleByDaytimeName(ScheduleRequest request) throws Exception {
        if (StringUtils.hasLength(request.daytime.name)) {
            boolean success = mysql.insertOne(CinemaSql.addScheduleByDaytimeName(
                request.startTime,
                request.daytime.name
            ));
            return new ScheduleResponse(success ? "SUCCESS" : "FAIL", request.startTime, request.daytime);
        }
        throw new BadRequestException("Daytime name is empty");
    }

    public boolean deleteSchedule(String daytimeName) throws Exception {
        if (StringUtils.hasLength(daytimeName)) {
            boolean success = mysql.deleteOne(CinemaSql.deleteSchedule(daytimeName));
            return success;
        }
        throw new BadRequestException("Daytime name is empty");
    }

    public boolean updateScheduleByDaytimeName(ScheduleRequest request) throws Exception {
        if (StringUtils.hasLength(request.daytime.name)) {
            boolean success = mysql.updateOne(CinemaSql.updateScheduleByDaytimeName(
                request.startTime,
                request.daytime.name
            ));
            return success;
        }
        throw new BadRequestException("Daytime name is empty");
    }
}
