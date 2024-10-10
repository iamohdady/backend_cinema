package com.example.backend_cinema.mysql.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ShowtimeEntity {



    public Integer id;

    public Integer movies_id;
    public MovieEntity movies;

    public Integer room_id;
    public RoomEntity room;

    public Integer schedule_id;
    public ScheduleEntity schedule;

    public String endtime;
    public boolean status;
}

