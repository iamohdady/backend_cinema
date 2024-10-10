package com.example.backend_cinema.response;

import com.example.backend_cinema.mysql.entity.DaytimeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ScheduleRequest {

    @JsonProperty("start_time")
    public String startTime;

    public DaytimeEntity daytime;
}
