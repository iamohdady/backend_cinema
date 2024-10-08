package com.example.backend_cinema;

import com.example.backend_cinema.request.AddUserRequest;
import com.example.backend_cinema.utils.model.Constants;
import com.example.backend_cinema.utils.model.Pair;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.Date;

public class CinemaSql {

    public static Pair<String, MapSqlParameterSource> selectAllUser() {
        String sql = "SELECT * FROM member ORDER BY id DESC";
        MapSqlParameterSource map = new MapSqlParameterSource();
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectByUsername(String username) {
        String sql = "SELECT * FROM member WHERE username = :username";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("username", username);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectUser(String username, String password) {
        String sql = "SELECT * FROM member WHERE username = :username AND password = :password";
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("username", username)
                .addValue("password", password);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> updateUserLastTime(String username) {
        String sql = "UPDATE member SET last_login = :lastLogin WHERE username = :username";
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("username", username)
                .addValue("lastLogin", new Date());
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> updateUserTokens(String username, String accessToken, String refreshToken) {
        String sql = "UPDATE member SET access_token = :accessToken, refresh_token = :refreshToken WHERE username = :username";
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("username", username)
                .addValue("accessToken", accessToken)
                .addValue("refreshToken", refreshToken);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> insertUser(String username,  String password, String fullname,
                                                                 String address, String phone, String birthday,
                                                                 String email, String image) {
        String sql = "INSERT INTO member (username, password, fullname, address, phone, birthday, email, image, status, role) " +
            "VALUES (:username, :password, :fullname, :address, :phone, :birthday, :email, :image, 'ACTIVE', 'READ')";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("username", username)
            .addValue("password", password)
            .addValue("fullname", fullname)
            .addValue("address", address)
            .addValue("phone", phone)
            .addValue("birthday", birthday)
            .addValue("email", email)
            .addValue("image", image);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> updateUserPassword(String username, String newPassword) {
        String sql = "UPDATE member SET password = :newPassword, status = :status WHERE username = :username";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("username", username)
            .addValue("newPassword", newPassword)
            .addValue("status", Constants.UserStatus.ACTIVE);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> updateRole(String username, String role) {
        String sql = "UPDATE member SET role = :role WHERE username = :username";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("username", username)
            .addValue("role", role);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> resetUserPassword(String username, String newPassword) {
        String sql = "UPDATE member SET password = :newPassword, status = 'INVITED' WHERE username = :username";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("username", username)
            .addValue("newPassword", newPassword);
        return new Pair<>(sql, map);
    }
}
