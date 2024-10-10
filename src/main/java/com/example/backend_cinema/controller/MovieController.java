package com.example.backend_cinema.controller;

import com.example.backend_cinema.mysql.entity.MovieEntity;
import com.example.backend_cinema.request.MovieRequest;
import com.example.backend_cinema.request.MovieNameRequest;
import com.example.backend_cinema.response.CountResponse;
import com.example.backend_cinema.service.MovieService;
import com.example.backend_cinema.service.ShowtimeService;
import com.example.backend_cinema.utils.model.CinemaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard/movie")
public class MovieController {

    private final MovieService service;

    private final ShowtimeService showtimeService;

    @Autowired
    public MovieController(MovieService service, ShowtimeService showtimeService) {
        this.service = service;
        this.showtimeService = showtimeService;
    }

    @PostMapping("/add")
    public ResponseEntity<CinemaResponse<String>> add(@RequestBody MovieRequest request) throws Exception {
        boolean success = service.add(request);
        return new ResponseEntity<>(new CinemaResponse<>(success ? "SUCCESS" : "FAIL"), HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<CinemaResponse<List<MovieEntity>>> list() {
        return new ResponseEntity<>(new CinemaResponse<>(service.list()), HttpStatus.OK);
    }

    @PostMapping("/detail")
    public ResponseEntity<CinemaResponse<MovieEntity>> getMovieDetails(@RequestBody MovieNameRequest request) throws Exception {
        MovieEntity movie = service.getMovieDetails(request.name);
        return new ResponseEntity<>(
            new CinemaResponse<>(movie),
            HttpStatus.OK
        );
    }

    @PostMapping("/detail/id")
    public ResponseEntity<CinemaResponse<MovieEntity>> getMovieDetailsId(@RequestBody MovieNameRequest request) throws Exception {
        MovieEntity movie = showtimeService.getMovieDetail(request.id);
        return new ResponseEntity<>(
            new CinemaResponse<>(movie),
            HttpStatus.OK
        );
    }

    @PostMapping("/update_movie")
    public ResponseEntity<CinemaResponse<String>> updateUser(@RequestBody MovieRequest request) throws Exception {
        boolean success = service.updateMovie(request);
        return new ResponseEntity<>(new CinemaResponse<>(success ? "SUCCESS" : "FAIL"), HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<CinemaResponse<String>> deleteMovie(@RequestBody MovieNameRequest request) throws Exception {
        boolean success = service.deleteMovie(request.name);
        return new ResponseEntity<>(new CinemaResponse<>(success ? "SUCCESS" : "FAIL"), HttpStatus.OK);
    }

    @PostMapping("/count")
    public ResponseEntity<CinemaResponse<CountResponse>> countMovie() throws Exception {
        CountResponse countResponse = service.countMovies();
        return new ResponseEntity<>(new CinemaResponse<>(countResponse), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<CinemaResponse<List<MovieEntity>>> searchMovies(@RequestBody MovieNameRequest request) throws Exception {
        List<MovieEntity> movies = service.searchMovies(request.name);
        CinemaResponse<List<MovieEntity>> response = new CinemaResponse<>(movies);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/upcoming")
    public ResponseEntity<CinemaResponse<List<MovieEntity>>> getUpcomingMovies() throws Exception {
        List<MovieEntity> movies = service.getUpcomingMovies();
        CinemaResponse<List<MovieEntity>> response = new CinemaResponse<>(movies);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
