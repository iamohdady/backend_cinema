package com.example.backend_cinema.mysql.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ScheduleEntity {

    public Integer id;

    @JsonProperty("start_time")
    public String startTime;

    public String daytime_id;
    public DaytimeEntity daytime;
}
