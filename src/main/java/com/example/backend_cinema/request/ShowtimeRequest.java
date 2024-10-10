package com.example.backend_cinema.request;

import com.example.backend_cinema.mysql.entity.MovieEntity;
import com.example.backend_cinema.mysql.entity.RoomEntity;
import com.example.backend_cinema.mysql.entity.ScheduleEntity;

public class ShowtimeRequest {
    public MovieEntity movie;

    public RoomEntity room;

    public ScheduleEntity schedule;

    public boolean status;
}
