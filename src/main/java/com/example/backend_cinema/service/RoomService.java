package com.example.backend_cinema.service;

import com.example.backend_cinema.CinemaSql;
import com.example.backend_cinema.mysql.MySqlConnector;
import com.example.backend_cinema.mysql.entity.MovieEntity;
import com.example.backend_cinema.mysql.entity.RoomEntity;
import com.example.backend_cinema.mysql.entity.SeatEntity;
import com.example.backend_cinema.request.RoomRequest;
import com.example.backend_cinema.response.SeatInRoomResponse;
import com.example.backend_cinema.utils.exception.BadRequestException;
import com.example.backend_cinema.utils.model.Constants;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class RoomService {

    private final MySqlConnector mysql;

    @Autowired
    public RoomService(MySqlConnector mysql) {
        this.mysql = mysql;
    }

    public boolean add(RoomRequest request) throws Exception {
        if (StringUtils.hasLength(request.name)) {
            if (checkExisted(request.name)) {
                throw new BadRequestException("Room already exists");
            }
            boolean success = mysql.insertOne(
                CinemaSql.insertRoom(
                    request.name,
                    request.capacity,
                    Constants.rateRoom.NORMAL,
                    Constants.statusRoom.FALSE
                )
            );
            if (success) {
                RoomEntity savedRoom = mysql.selectOne(CinemaSql.selectRoomByName(request.name), RoomEntity.class);
                for (int i = 1; i <= request.capacity; i++) {
                    mysql.insertOne(CinemaSql.insertSeat(
                        generateSeatName(i),
                        Constants.statusSeat.FALSE,
                        savedRoom.id
                    ));
                }
                return success;
            }
            throw new Exception("Failed to insert room");
        }
        throw new BadRequestException("Room name is empty");
    }

    public List<RoomEntity> list() {
        return mysql.selectList(CinemaSql.selectAllRoom(), RoomEntity.class);
    }

    public RoomEntity detail(String name) throws Exception {
        if (StringUtils.hasLength(name)) {
            return mysql.selectOne(CinemaSql.selectRoomByName(name), RoomEntity.class);
        }
        throw new BadRequestException("Name is empty");
    }

    public List<SeatInRoomResponse> getSeatsByRoomName(String roomName) throws Exception {
        if (!StringUtils.hasLength(roomName)) {
            throw new BadRequestException("Room name is empty");
        }
        List<SeatInRoomResponse> seatList = mysql.selectList(
            CinemaSql.selectSeatsByRoomName(roomName),
            SeatInRoomResponse.class
        );
        if (seatList == null || seatList.isEmpty()) {
            throw new BadRequestException("No seats found for room: " + roomName);
        }
        return seatList;
    }

    public boolean updateStatus(RoomRequest request) throws Exception {
        if (StringUtils.hasLength(request.name)) {
            String currentStatus = mysql.selectOne(CinemaSql.selectCurrentStatusByName(request.name), String.class);
            String newStatus = currentStatus.equals(Constants.statusRoom.TRUE)
                ? Constants.statusRoom.FALSE
                : Constants.statusRoom.TRUE;
            boolean success = mysql.updateOne(CinemaSql.updateStatusRoom(request.name, newStatus));
            return success;
        }
        throw new BadRequestException("Name is empty");
    }

    private String generateSeatName(int seatNumber) {
        char letter = (char) ('A' + (seatNumber - 1) / 10);
        int number = seatNumber % 10 == 0 ? 10 : seatNumber % 10;
        return String.valueOf(letter) + number;
    }

    private boolean checkExisted(String name) throws Exception {
        if (Strings.hasLength(name)) {
            MovieEntity movie = mysql.selectOne(CinemaSql.selectRoomByName(name), MovieEntity.class);
            return movie != null;
        }
        throw new BadRequestException("name is empty");
    }
}
