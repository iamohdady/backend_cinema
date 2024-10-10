package com.example.backend_cinema.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieRequest {

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
