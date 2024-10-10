package com.example.backend_cinema.request;

import lombok.Data;

@Data
public class BookTicketRequest {

    public Integer showtimeId;

    public Integer seatId;
}
