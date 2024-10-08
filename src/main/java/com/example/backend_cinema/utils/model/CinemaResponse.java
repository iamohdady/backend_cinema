package com.example.backend_cinema.utils.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CinemaResponse<T> {
    @JsonProperty("result")
    public T result;

    public CinemaResponse(T result) {
            this.result = result;
        }
}

