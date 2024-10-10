package com.example.backend_cinema.response;

import com.example.backend_cinema.mysql.entity.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DetailShowtimeResponse {

    public Integer id;

    public Integer movies_id;
    public MovieEntity movies;

    public Integer room_id;
    public RoomEntity room;

    public Integer schedule_id;
    public ScheduleEntity schedule;

    public String endtime;
    public boolean status;

    @JsonProperty("result")
    public String result;

    @JsonProperty("showtime")
    public ShowtimeEntity showtime;

    public DetailShowtimeResponse(String result, ShowtimeEntity showtime) {
        this.result = result;
        this.showtime = showtime;
    }
}
