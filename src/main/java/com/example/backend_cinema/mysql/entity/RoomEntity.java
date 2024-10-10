package com.example.backend_cinema.mysql.entity;

import lombok.Data;

@Data
public class RoomEntity {

    public Integer id;

    public String name;

    public Integer capacity;

    public String rate;

    public String status;
}
