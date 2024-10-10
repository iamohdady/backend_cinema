package com.example.backend_cinema.service;

import com.example.backend_cinema.CinemaSql;
import com.example.backend_cinema.mysql.MySqlConnector;
import com.example.backend_cinema.mysql.entity.ShowtimeEntity;
import com.example.backend_cinema.mysql.entity.UserEntity;
import com.example.backend_cinema.request.AddUserRequest;
import com.example.backend_cinema.request.ShowtimeRequest;
import com.example.backend_cinema.response.ShowtimeResponse;
import com.example.backend_cinema.response.UserResponse;
import com.example.backend_cinema.utils.crypt.CryptUtils;
import com.example.backend_cinema.utils.exception.BadRequestException;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ShowtimeService {

    private final MySqlConnector mysql;

    @Autowired
    public ShowtimeService(MySqlConnector mysql) {
        this.mysql = mysql;
    }

    public List<ShowtimeEntity> list() {
        return mysql.selectList(CinemaSql.selectShowtime(), ShowtimeEntity.class);
    }

    public ShowtimeResponse add(ShowtimeRequest request) throws Exception {
        if (StringUtils.hasLength(request.movie.name) && StringUtils.hasLength((request.room.name))) {
//            if (checkExisted(request.username)) {
//                throw new BadRequestException("Username already exists");
//            }
            boolean success = mysql.insertOne(
                CinemaSql.insertShowtime(
                    request.movie.name,
                    request.room.name,
                    request.schedule.startTime,
                    request.status
                )
            );
            if (success) {
                // Retrieve the inserted showtime entity by fetching the latest entry or by other means (e.g., based on IDs)
                ShowtimeEntity insertedShowtime = mysql.selectOne(
                    CinemaSql.selectShowtimeByDetails(
                        request.movie.name,
                        request.room.name,
                        request.schedule.startTime
                    ),
                    ShowtimeEntity.class
                );

                // Return a ShowtimeResponse with "SUCCESS" and the retrieved showtime entity
                return new ShowtimeResponse("SUCCESS", insertedShowtime);
            } else {
                // Return a failure response
                return new ShowtimeResponse("FAIL", null);
            }
        }
        throw new BadRequestException("Movie or room name is empty");
    }

    public ShowtimeEntity getShowtimeDetails(String movieName, String roomName, String startTime) throws Exception {
        if (StringUtils.hasLength(movieName) && StringUtils.hasLength(roomName) && StringUtils.hasLength(startTime)) {
            return mysql.selectOne(
                CinemaSql.selectShowtimeByDetails(movieName, roomName, startTime),
                ShowtimeEntity.class
            );
        }
        throw new BadRequestException("Movie name, room name, or start time is empty");
    }


//    private boolean checkExisted(String name) throws Exception {
//        if (Strings.hasLength(username)) {
//            UserEntity user = mysql.selectOne(CinemaSql.selectByUsername(username), UserEntity.class);
//            return user != null;
//        }
//        throw new BadRequestException("username is empty");
//    }
}
