package com.example.backend_cinema.mysql.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SeatEntity {

    public Integer id;

    public String name;

    @JsonProperty("is_booked")
    public boolean isBooked;

    public RoomEntity room;
}
