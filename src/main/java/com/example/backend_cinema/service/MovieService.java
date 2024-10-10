package com.example.backend_cinema.service;

import com.example.backend_cinema.CinemaSql;
import com.example.backend_cinema.mysql.MySqlConnector;
import com.example.backend_cinema.mysql.entity.MovieEntity;
import com.example.backend_cinema.request.MovieRequest;
import com.example.backend_cinema.response.CountResponse;
import com.example.backend_cinema.utils.exception.BadRequestException;
import com.example.backend_cinema.utils.model.Pair;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MovieService {

    private final MySqlConnector mysql;

    @Autowired
    public MovieService(MySqlConnector mysql) {
        this.mysql = mysql;
    }

    public boolean add(MovieRequest request) throws Exception {
        if (StringUtils.hasLength(request.name)) {
            if (checkExisted(request.name)) {
                throw new BadRequestException("Username already exists");
            }
            boolean success = mysql.insertOne(
                CinemaSql.insertMovie(
                    request.name,
                    request.image,
                    request.startDate,
                    request.duration,
                    request.rated,
                    request.trailer,
                    request.category,
                    request.director,
                    request.actor,
                    request.price
                )
            );
            return success;
        }
        throw new BadRequestException("name is empty");
    }

    public List<MovieEntity> list() {
        return mysql.selectList(CinemaSql.selectAllMovies(), MovieEntity.class);
    }

    public MovieEntity getMovieDetails(String name) throws Exception {
        if (StringUtils.hasLength(name)) {
            return mysql.selectOne(CinemaSql.selectMovieByUsername(name), MovieEntity.class);
        }
        throw new BadRequestException("Name is empty");
    }

    public boolean updateMovie(MovieRequest request) throws Exception {
        if (StringUtils.hasLength(request.name)) {
            boolean success = mysql.updateOne(CinemaSql.updateMovie(
                request.name,
                request.image,
                request.startDate,
                request.duration,
                request.rated,
                request.trailer,
                request.category,
                request.director,
                request.actor,
                request.price
            ));
            return success; // Trả về kết quả
        }
        throw new BadRequestException("Name is empty");
    }

    public boolean deleteMovie(String name) throws Exception {
        if (StringUtils.hasLength(name)) {
            boolean success = mysql.deleteOne(CinemaSql.deleteMovie(name));
            return success;
        }
        throw new BadRequestException("Name is empty");
    }

    public CountResponse countMovies() throws Exception {
        Pair<String, MapSqlParameterSource> sqlPair = CinemaSql.countMovie();
        Integer count = mysql.count(sqlPair);
        return new CountResponse(count != null ? count : 0);
    }

    public List<MovieEntity> searchMovies(String searchTerm) throws Exception {
        if (StringUtils.hasLength(searchTerm)) {
            return mysql.selectList(CinemaSql.searchMovies(searchTerm), MovieEntity.class);
        }
        throw new BadRequestException("Search term is empty");
    }

    public List<MovieEntity> getUpcomingMovies() throws Exception {
        return mysql.selectList(CinemaSql.selectMovieComming(), MovieEntity.class);
    }

    private boolean checkExisted(String name) throws Exception {
        if (Strings.hasLength(name)) {
            MovieEntity movie = mysql.selectOne(CinemaSql.selectMovieByName(name), MovieEntity.class);
            return movie != null;
        }
        throw new BadRequestException("name is empty");
    }
}
