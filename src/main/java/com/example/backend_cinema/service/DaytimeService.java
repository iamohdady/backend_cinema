package com.example.backend_cinema.service;

import com.example.backend_cinema.CinemaSql;
import com.example.backend_cinema.mysql.MySqlConnector;
import com.example.backend_cinema.mysql.entity.DaytimeEntity;
import com.example.backend_cinema.mysql.entity.UserEntity;
import com.example.backend_cinema.request.AddUserRequest;
import com.example.backend_cinema.request.DaytimeRequest;
import com.example.backend_cinema.response.DaytimeResponse;
import com.example.backend_cinema.response.UserResponse;
import com.example.backend_cinema.utils.crypt.CryptUtils;
import com.example.backend_cinema.utils.exception.BadRequestException;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DaytimeService {

    private final MySqlConnector mysql;

    @Autowired
    public DaytimeService(MySqlConnector mysql) {
        this.mysql = mysql;
    }

    public DaytimeResponse add(DaytimeRequest request) throws Exception {
        if (StringUtils.hasLength(request.name)) {
            if (checkExisted(request.name)) {
                throw new BadRequestException("Daytime already exists");
            }
            boolean success = mysql.insertOne(
                CinemaSql.addDaytime(
                    request.name,
                    request.daytime
                )
            );
            return new DaytimeResponse(success ? "SUCCESS" : "FAIL", request.name, request.daytime);
        }
        throw new BadRequestException("username is empty");
    }

    public List<DaytimeEntity> list() {
        return mysql.selectList(CinemaSql.selectDaytime(), DaytimeEntity.class);
    }

    public boolean updateDaytime(DaytimeRequest request) throws Exception {
        if (StringUtils.hasLength(request.name)) {
            boolean success = mysql.updateOne(CinemaSql.updateDaytime(
                request.name,
                request.daytime
            ));
            return success;
        }
        throw new BadRequestException("Daytime is empty");
    }

    public boolean deleteDaytime(String name) throws Exception {
        if (StringUtils.hasLength(name)) {
            boolean success = mysql.deleteOne(CinemaSql.deleteDaytime(name));
            return success;
        }
        throw new BadRequestException("Username is empty");
    }

    private boolean checkExisted(String name) throws Exception {
        if (Strings.hasLength(name)) {
            DaytimeEntity daytime = mysql.selectOne(CinemaSql.selectDaytimeByName(name), DaytimeEntity.class);
            return daytime != null;
        }
        throw new BadRequestException("name is empty");
    }

}
