package com.example.backend_cinema.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DaytimeResponse {

    @JsonProperty("result")
    public String result;

    @JsonProperty("name")
    public String name;

    @JsonProperty("daytime")
    public String daytime;

    public DaytimeResponse(String result, String name, String daytime) {
        this.result = result;
        this.name = name;
        this.daytime = daytime;
    }
}
