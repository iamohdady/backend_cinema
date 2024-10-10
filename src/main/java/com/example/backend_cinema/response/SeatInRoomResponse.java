package com.example.backend_cinema.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SeatInRoomResponse {

    @JsonProperty("seat_name")
    public String name;

    @JsonProperty("is_booked")
    public boolean isBooked;
}
