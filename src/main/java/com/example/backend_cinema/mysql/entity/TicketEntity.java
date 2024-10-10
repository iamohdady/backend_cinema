package com.example.backend_cinema.mysql.entity;

import lombok.Data;

@Data
public class TicketEntity {

    public Integer id;

    public Double price;

    public String code_qr;

    public String description;

    public Integer showtime_id;
    public ShowtimeEntity showtime;

    public BillEntity bill_id;

    public SeatEntity seat_id;

    public Integer seat_booking_id;
    public SeatBookingEntity seat_booking;
}
