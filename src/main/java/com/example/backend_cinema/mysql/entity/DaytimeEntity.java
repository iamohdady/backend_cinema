package com.example.backend_cinema.mysql.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DaytimeEntity {

    @JsonProperty("id")
    public Integer id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("day_time")
    public String dayTime;
}
