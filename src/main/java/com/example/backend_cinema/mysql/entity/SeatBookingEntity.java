package com.example.backend_cinema.mysql.entity;

import lombok.Data;

@Data
public class SeatBookingEntity {

    public Integer id;

    public Integer showtime_id;
    public ShowtimeEntity showtime;

    public Integer seat_id;
    public SeatEntity seat;

    public boolean is_booked;
}
