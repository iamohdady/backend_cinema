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

    public static Pair<String, MapSqlParameterSource> selectMemberByUsername(String username) {
        String sql = "SELECT * FROM member WHERE username = :username";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("username", username);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> updateMember(String username, String fullname, String address,
                                                                 String phone, String birthday, String email, String image) {
        String sql = "UPDATE member SET fullname = :fullname, address = :address, phone = :phone, " +
            "birthday = :birthday, email = :email, image = :image WHERE username = :username";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("username", username)
            .addValue("fullname", fullname)
            .addValue("address", address)
            .addValue("phone", phone)
            .addValue("birthday", birthday)
            .addValue("email", email)
            .addValue("image", image);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> deleteUser(String username) {
        String sql = "DELETE FROM member WHERE username = :username";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("username", username);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> countMember() {
        String sql = "SELECT COUNT(*) FROM member";
        MapSqlParameterSource map = new MapSqlParameterSource();
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> insertMovie(String name,  String image, String startDate,
                                                                 Integer duration, String rated, String trailer,
                                                                 String category, String director, String actor, Double price) {
        String sql = "INSERT INTO movies (name, image, start_date, duration, rated, trailer, category, price, actor, director) " +
            "VALUES (:name, :image, :startDate, :duration, :rated, :trailer, :category, :price, :actor, :director)";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("name", name)
            .addValue("image", image)
            .addValue("startDate", startDate)
            .addValue("duration", duration)
            .addValue("rated", rated)
            .addValue("trailer", trailer)
            .addValue("category", category)
            .addValue("price", price)
            .addValue("actor", actor)
            .addValue("director", director);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectAllMovies() {
        String sql = "SELECT * FROM movies ORDER BY id DESC";
        MapSqlParameterSource map = new MapSqlParameterSource();
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectMovieByName(String name) {
        String sql = "SELECT * FROM movies WHERE name = :name";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("name", name);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectMovieByUsername(String name) {
        String sql = "SELECT * FROM movies WHERE name = :name";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("name", name);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> updateMovie(String name,  String image, String startDate,
                                                                  Integer duration, String rated, String trailer,
                                                                  String category, String director, String actor, Double price) {
        String sql = "UPDATE movies SET name = :name, image = :image, start_date = :startDate, duration = :duration, rated = :rated" +
            ", trailer = :trailer, category = :category, director = :director, actor = :actor, price = :price WHERE name = :name";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("name", name)
            .addValue("image", image)
            .addValue("startDate", startDate)
            .addValue("duration", duration)
            .addValue("rated", rated)
            .addValue("trailer", trailer)
            .addValue("category", category)
            .addValue("price", price)
            .addValue("actor", actor)
            .addValue("director", director);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> deleteMovie(String name) {
        String sql = "DELETE FROM movies WHERE name = :name";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("name", name);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> countMovie() {
        String sql = "SELECT COUNT(*) FROM movies";
        MapSqlParameterSource map = new MapSqlParameterSource();
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> searchMovies(String searchTerm) {
        String sql = "SELECT * FROM movies WHERE name LIKE :searchTerm";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("searchTerm", "%" + searchTerm + "%");
        return new Pair<>(sql, params);
    }

    public static Pair<String, MapSqlParameterSource> selectMovieComming() {
        String sql = "SELECT * FROM movies WHERE start_date > NOW() ORDER BY start_date ASC";
        MapSqlParameterSource map = new MapSqlParameterSource();
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectMovieById(Integer id) {
        String sql = "SELECT * FROM movies WHERE id = :id";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("id", id);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> insertRoom(String name, Integer capacity, String rate, String status) {
        String sql = "INSERT INTO room (name, capacity, rate, status)" +
            "VALUES (:name, :capacity, :rate, :status)";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("name", name)
            .addValue("capacity", capacity)
            .addValue("rate", rate)
            .addValue("status", status);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectAllRoom() {
        String sql = "SELECT * FROM room";
        MapSqlParameterSource map = new MapSqlParameterSource();
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectRoomById(Integer id) {
        String sql = "SELECT * FROM room WHERE id = :id";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("id", id);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectRoomByName(String name) {
        String sql = "SELECT * FROM room WHERE name = :name";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("name", name);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> insertSeat(String seatName, String isBooked, Integer roomId) {
        String sql = "INSERT INTO seat (name, is_booked, room_id) VALUES (:seatName, :isBooked, :roomId);";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("seatName", seatName)
            .addValue("isBooked", isBooked)
            .addValue("roomId", roomId);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectSeatsByRoomName(String roomName) {
        String sql = "SELECT s.id, s.name AS name, s.is_booked, " +
            "r.name AS room_name, r.capacity, r.description, r.rate AS room_rate, r.status " +
            "FROM seat s " +
            "JOIN room r ON s.room_id = r.id " +
            "WHERE r.name = :roomName";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("roomName", roomName);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> updateStatusRoom(String newStatus, String name) {
        String sql = "UPDATE room SET status = :status WHERE name = :name";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("status", newStatus)
            .addValue("name", name);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectCurrentStatusByName(String name) {
        String sql = "SELECT status FROM room WHERE name = :name";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("name", name);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectDaytimeByName(String name) {
        String sql = "SELECT * FROM daytime WHERE name = :name";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("name", name);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> addDaytime(String name, String dayTime) {
        String sql = "INSERT INTO daytime (name, day_time) VALUES (:name, :dayTime)";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("name", name)
            .addValue("dayTime", dayTime);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectDaytime() {
        String sql = "SELECT * FROM daytime";
        MapSqlParameterSource map = new MapSqlParameterSource();
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectDaytimeById(Integer id) {
        String sql = "SELECT * FROM daytime WHERE id = :id";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("id", id);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> updateDaytime(String name, String dayTime) {
        String sql = "UPDATE daytime SET day_time = :dayTime WHERE name = :name";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("name", name)
            .addValue("dayTime", dayTime);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> deleteDaytime(String name) {
        String sql = "DELETE FROM daytime WHERE name = :name";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("name", name);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectScheduleById(Integer id) {
        String sql = "SELECT * FROM schedule WHERE id = :id";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("id", id);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectScheduleByDaytimeName(String daytimeName) {
        String sql = "SELECT s.* FROM schedule s " +
            "JOIN daytime d ON d.id = s.daytime_id " +
            "WHERE d.name = :daytimeName";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("daytimeName", daytimeName);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> addScheduleByDaytimeName(String startTime, String daytimeName) {
        String sql = "INSERT INTO schedule (start_time, daytime_id) " +
            "VALUES (:startTime, (SELECT id FROM daytime WHERE name = :daytimeName))";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("startTime", startTime)
            .addValue("daytimeName", daytimeName);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> updateScheduleByDaytimeName(String startTime, String daytimeName) {
        String sql = "UPDATE schedule SET start_time = :startTime " +
            "WHERE daytime_id = (SELECT id FROM daytime WHERE name = :daytimeName)";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("startTime", startTime)
            .addValue("daytimeName", daytimeName);
        return new Pair<>(sql, map);
    }

    // Hàm SQL để xóa schedule theo daytime name
    public static Pair<String, MapSqlParameterSource> deleteSchedule(String daytimeName) {
        String sql = "DELETE s FROM schedule s " +
            "JOIN daytime d ON d.id = s.daytime_id " +
            "WHERE d.name = :daytimeName";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("daytimeName", daytimeName);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectShowtime() {
        String sql = "SELECT * FROM showtime";
        MapSqlParameterSource map = new MapSqlParameterSource();
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> insertShowtime(String nameMovie, String nameRoom, String startTime, boolean status, String endtime) {
        String sql = "INSERT INTO showtime (movies_id, room_id, schedule_id, status, endtime) " +
            "VALUES ((SELECT id FROM movies WHERE name = :nameMovie), " +
            "(SELECT id FROM room WHERE name = :nameRoom), " +
            "(SELECT id FROM schedule WHERE start_time = :startTime), :status, :endtime)";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("nameMovie", nameMovie)
            .addValue("nameRoom", nameRoom)
            .addValue("startTime", startTime)
            .addValue("status", status)
            .addValue("endtime", endtime);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectShowtimeByLastInserted() {
        String sql = "SELECT * FROM showtime ORDER BY id DESC LIMIT 1";
        MapSqlParameterSource map = new MapSqlParameterSource();
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectDetailShowtime(Integer id) {
        String sql = "SELECT * FROM showtime WHERE id = :id";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("id", id);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> updateEndtime(Integer id, String endtime) {
        String sql = "UPDATE showtime SET endtime = :endtime WHERE id = :id";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("endtime", endtime);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> findAvailableRoomsByDayTime(Integer daytimeId) {
        String sql = "SELECT r.* FROM room r WHERE r.id NOT IN (" +
            "SELECT s.room_id FROM showtime s " +
            "JOIN schedule sch ON s.schedule_id = sch.id " +
            "WHERE sch.daytime_id = :daytimeId);";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("daytimeId", daytimeId);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> insertSeatBooking(Integer showtimeId, String roomName) {
        String sql = "INSERT INTO seat_booking (showtime_id, seat_id, is_booked) " +
            "SELECT :showtimeId, s.id, FALSE " +
            "FROM seat s " +
            "WHERE s.room_id = (SELECT id FROM room WHERE name = :roomName)";

        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("showtimeId", showtimeId)
            .addValue("roomName", roomName);

        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> findBySeatIdAndShowtimeId(Integer seatId, Integer showtimeId) {
        String sql = "SELECT * FROM seat_booking WHERE seat_id = :seatId AND showtime_id = :showtimeId";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("seatId", seatId)
            .addValue("showtimeId", showtimeId);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> bookSeat(Integer showtimeId, Integer seatId) {
        String sql = "UPDATE seat_booking SET is_booked = TRUE WHERE showtime_id = :showtimeId AND seat_id = :seatId";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("showtimeId", showtimeId)
            .addValue("seatId", seatId);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> countByShowtimeId(Integer showtimeId) {
        String sql = "SELECT COUNT(*) FROM ticket WHERE showtime_id = :showtimeId";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("showtimeId", showtimeId);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> saveTicket(Double price, Integer billId, Integer seatBookingId) {
        String sql = "INSERT INTO ticket (price, bill_id, seat_booking_id) VALUES (:price, :billId, :seatBookingId);";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("price", price)
            .addValue("billId", billId)
            .addValue("seatBookingId", seatBookingId);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> saveBill(String paymentTime, Double totalAmount, Integer memberId, boolean status) throws Exception {
        String sql = "INSERT INTO bill (payment_time, total_amount, member_id, status) VALUES (:paymentTime, :totalAmount, :memberId, :status);";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("paymentTime", paymentTime)
            .addValue("totalAmount", totalAmount)
            .addValue("memberId", memberId)
            .addValue("status", status);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> insertRoomBooking(Integer showtimeId, String roomName, Boolean isBooked) {
        String sql = "INSERT INTO room_booking (showtime_id, room_id, isbooked) " +
            "VALUES (:showtimeId, (SELECT id FROM room WHERE name = :roomName), :isBooked)";

        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("showtimeId", showtimeId)
            .addValue("roomName", roomName)
            .addValue("isBooked", isBooked);

        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectSeatById(Integer id) {
        String sql = "SELECT * FROM seat WHERE id = :id";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("id", id);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectTicket(Integer id) {
        String sql = "SELECT * FROM ticket WHERE id = :id";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("id", id);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectSeatBooking(Integer id) {
        String sql = "SELECT * FROM seat_booking WHERE id = :id";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("id", id);
        return new Pair<>(sql, map);
    }

    public static Pair<String, MapSqlParameterSource> selectBill(Integer id) {
        String sql = "SELECT * FROM bill WHERE id = :id";
        MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("id", id);
        return new Pair<>(sql, map);
    }
}
