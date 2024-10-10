package com.example.backend_cinema.mysql.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScheduleEntity {

    @JsonProperty("start_time")
    public String startTime;

    public DaytimeEntity daytime;
}
