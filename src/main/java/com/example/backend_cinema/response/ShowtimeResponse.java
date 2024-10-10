package com.example.backend_cinema.response;

import com.example.backend_cinema.mysql.entity.ShowtimeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShowtimeResponse {

    @JsonProperty("result")
    public String result;

    @JsonProperty("showtime")
    public ShowtimeEntity showtime;

    public ShowtimeResponse(String result, ShowtimeEntity showtime) {
        this.result = result;
        this.showtime = showtime;
    }
}
