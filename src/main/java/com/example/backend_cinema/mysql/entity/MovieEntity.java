package com.example.backend_cinema.mysql.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MovieEntity {

    public String name;

    public String image;

    @JsonProperty("start_date")
    public String startDate;

    public Integer duration;

    public String rated;

    public String trailer;

    public String category;

    public String director;

    public String actor;

    public double price;
}
