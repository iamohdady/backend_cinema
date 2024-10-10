package com.example.backend_cinema.service;

import com.example.backend_cinema.mysql.entity.DaytimeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ScheduleResponse {

    @JsonProperty("result")
    public String result;

    @JsonProperty("start_time")
    public String startTime;

    @JsonProperty("daytime")
    public DaytimeEntity daytime;

    public ScheduleResponse(String result, String startTime, DaytimeEntity daytime) {
        this.result = result;
        this.startTime = startTime;
        this.daytime = daytime;
    }
}
