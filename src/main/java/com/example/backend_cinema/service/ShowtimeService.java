package com.example.backend_cinema.service;

import com.example.backend_cinema.CinemaSql;
import com.example.backend_cinema.mysql.MySqlConnector;
import com.example.backend_cinema.mysql.entity.*;
import com.example.backend_cinema.request.ShowtimeRequest;
import com.example.backend_cinema.response.DetailShowtimeResponse;
import com.example.backend_cinema.response.ShowtimeResponse;
import com.example.backend_cinema.utils.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ShowtimeService {

    private final MySqlConnector mysql;

    @Autowired
    public ShowtimeService(MySqlConnector mysql) {
        this.mysql = mysql;
    }

    public List<ShowtimeEntity> list() throws Exception {
        List<ShowtimeEntity> showtimeList = mysql.selectList(CinemaSql.selectShowtime(), ShowtimeEntity.class);
        for (ShowtimeEntity showtime : showtimeList) {
            if (showtime.getMovies_id() != null) {
                MovieEntity movies = getMovieDetail(showtime.getMovies_id());
                showtime.setMovies(movies);
            }
            if (showtime.getRoom_id() != null) {
                RoomEntity room = getRoomDetail(showtime.getRoom_id());
                showtime.setRoom(room);
            }
            if (showtime.getSchedule_id() != null) {
                ScheduleEntity schedule = getScheduleDetail(showtime.getSchedule_id());
                if (schedule.getDaytime_id() != null) {
                    DaytimeEntity daytime = getDaytimeDetail(Integer.valueOf(schedule.getDaytime_id()));
                    schedule.setDaytime(daytime);
                }
                showtime.setSchedule(schedule);
            } else {
                System.out.println("No schedule found for showtime.");
            }
        }
        return showtimeList;
    }

    public ShowtimeEntity add(ShowtimeRequest request) throws Exception {
        if (StringUtils.hasLength(request.movie.name) && StringUtils.hasLength(request.room.name)) {
            MovieEntity movie = mysql.selectOne(CinemaSql.selectMovieByName(request.movie.name), MovieEntity.class);
            if (movie == null) {
                throw new BadRequestException("Movie not found");
            }
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime startTime = LocalTime.parse(request.schedule.startTime, timeFormatter);
            LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), startTime);
            LocalDateTime endTime = startDateTime.plusMinutes(movie.getDuration());
            boolean success = mysql.insertOne(
                CinemaSql.insertShowtime(
                    request.movie.name,
                    request.room.name,
                    request.schedule.startTime,
                    request.status,
                    endTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                )
            );
            if (success) {
                ShowtimeEntity insertedShowtime = mysql.selectOne(
                    CinemaSql.selectShowtimeByLastInserted(),
                    ShowtimeEntity.class
                );
                if (insertedShowtime == null) {
                    throw new RuntimeException("Showtime was successfully added, but could not be retrieved.");
                }
                mysql.insertOne(
                    CinemaSql.insertSeatBooking(
                        insertedShowtime.getId(),
                        request.room.name
                    )
                );
                mysql.insertOne(
                    CinemaSql.insertRoomBooking(
                        insertedShowtime.getId(),
                        request.room.name,
                        true
                    )
                );
                if (insertedShowtime.getMovies_id() != null) {
                    MovieEntity movies = getMovieDetail(insertedShowtime.getMovies_id());
                    insertedShowtime.setMovies(movies);
                }
                if (insertedShowtime.getRoom_id() != null) {
                    RoomEntity room = getRoomDetail(insertedShowtime.getRoom_id());
                    insertedShowtime.setRoom(room);
                }
                if (insertedShowtime.getSchedule_id() != null) {
                    ScheduleEntity schedule = getScheduleDetail(insertedShowtime.getSchedule_id());
                    if (schedule.getDaytime_id() != null) {
                        DaytimeEntity daytime = getDaytimeDetail(Integer.valueOf(schedule.getDaytime_id()));
                        schedule.setDaytime(daytime);
                    }
                    insertedShowtime.setSchedule(schedule);
                } else {
                    System.out.println("No schedule found for showtime.");
                }

                return insertedShowtime;
            } else {
                throw new Exception("Failed to add showtime");
            }
        }
        throw new BadRequestException("Movie or room name is empty");
    }

    public ShowtimeEntity getShowtimeDetails(Integer id) throws Exception {
        ShowtimeEntity showtime = mysql.selectOne(CinemaSql.selectDetailShowtime(id), ShowtimeEntity.class);

        if (showtime == null) {
            throw new Exception("Showtime not found with ID: " + id);
        }
        if (showtime.getMovies_id() != null) {
            MovieEntity movies = getMovieDetail(showtime.getMovies_id());
            showtime.setMovies(movies);
        } else {
            System.out.println("No movies found for showtime.");
        }

        if (showtime.getRoom_id() != null) {
            RoomEntity room = getRoomDetail(showtime.getRoom_id());
            showtime.setRoom(room);
        } else {
            System.out.println("No room found for showtime.");
        }
        if (showtime.getSchedule_id() != null) {
            ScheduleEntity schedule = getScheduleDetail(showtime.getSchedule_id());
            if (schedule.getDaytime_id() != null) {
                DaytimeEntity daytime = getDaytimeDetail(Integer.valueOf(schedule.getDaytime_id()));
                schedule.setDaytime(daytime);
            }
            showtime.setSchedule(schedule);
        } else {
            System.out.println("No schedule found for showtime.");
        }
        return showtime;
    }

//    public LocalDateTime calculateEndTime(int showTimeId) throws Exception {
//        // Lấy thông tin ShowTime từ database
//        DetailShowtimeResponse showTime = mysql.selectOne(CinemaSql.selectDetailShowtime(showTimeId), DetailShowtimeResponse.class);
//        if (showTime == null) {
//            throw new BadRequestException("ShowTime not found");
//        }
//
//        // Lấy thông tin Schedule và Movie
//        ScheduleEntity schedule = showTime.getSchedule();
//        MovieEntity movie = showTime.getMovies();
//
//        if (schedule == null || movie == null) {
//            throw new BadRequestException("Schedule or Movie not found for the ShowTime");
//        }
//
//        // Lấy và xử lý thời gian bắt đầu
//        String startTimeStr = schedule.getStartTime();
//        if (startTimeStr == null || startTimeStr.isEmpty()) {
//            throw new BadRequestException("Start time is null or empty");
//        }
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//        LocalTime startTime = LocalTime.parse(startTimeStr, timeFormatter);
//
//        // Tính toán thời gian kết thúc
//        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), startTime);
//        LocalDateTime endDateTime = startDateTime.plusMinutes(movie.getDuration());
//
//        // Cập nhật thời gian kết thúc và lưu lại
//        showTime.setEndtime(endDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//        mysql.updateOne(CinemaSql.updateEndtime(showTime.getId(), showTime.getEndtime()));
//
//        return endDateTime;
//    }

    public MovieEntity getMovieDetail(Integer id) {
        return mysql.selectOne(CinemaSql.selectMovieById(id), MovieEntity.class);
    }

    public RoomEntity getRoomDetail(Integer id) {
        return mysql.selectOne(CinemaSql.selectRoomById(id), RoomEntity.class);
    }

    public DaytimeEntity getDaytimeDetail(Integer id) {
        return mysql.selectOne(CinemaSql.selectDaytimeById(id), DaytimeEntity.class);
    }

    public ScheduleEntity getScheduleDetail(Integer id) {
        return mysql.selectOne(CinemaSql.selectScheduleById(id), ScheduleEntity.class);
    }

    public String calculateEndTime(Integer movieId, String startTime) {
        MovieEntity movie = mysql.selectOne(CinemaSql.selectMovieById(movieId), MovieEntity.class);
        LocalTime startTimeLocal = LocalTime.parse(startTime);
        LocalTime endTimeLocal = startTimeLocal.plusMinutes(movie.getDuration());
        return endTimeLocal.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    private boolean checkExisted(Integer id) throws Exception {
        ShowtimeEntity showtime = mysql.selectOne(CinemaSql.selectDetailShowtime(id), ShowtimeEntity.class);
        return showtime != null;
    }
}
