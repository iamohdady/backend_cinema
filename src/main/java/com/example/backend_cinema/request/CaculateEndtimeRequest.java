package com.example.backend_cinema.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CaculateEndtimeRequest {

    @JsonProperty("movie_id")
    public Integer movieId;

    @JsonProperty("start_time")
    public String startTime;
}
