package com.example.backend_cinema.service;

import com.example.backend_cinema.CinemaSql;
import com.example.backend_cinema.mysql.MySqlConnector;
import com.example.backend_cinema.mysql.entity.*;
import com.example.backend_cinema.request.BookTicketRequest;
import com.example.backend_cinema.utils.exception.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final MySqlConnector mysql;

    @Autowired
    public TicketService(MySqlConnector mysql) {
        this.mysql = mysql;
    }

    public List<TicketEntity> bookTickets(List<BookTicketRequest> requests, String username) throws Exception {
        UserEntity user = mysql.selectOne(CinemaSql.selectMemberByUsername(username), UserEntity.class);
        if (user == null) {
            throw new BadRequestException("Không tìm thấy thông tin thành viên.");
        }

        BillEntity billEntity = new BillEntity();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedPaymentTime = dateFormat.format(new Date());
        billEntity.setPaymentTime(formattedPaymentTime);
        double totalPrice = 0.0;

        List<TicketEntity> bookedTickets = new ArrayList<>();

        for (BookTicketRequest ticketRequest : requests) {
            // Tìm thông tin đặt ghế
            SeatBookingEntity seatBooking = mysql.selectOne(
                CinemaSql.findBySeatIdAndShowtimeId(ticketRequest.seatId, ticketRequest.showtimeId),
                SeatBookingEntity.class
            );

            if (seatBooking == null || seatBooking.is_booked) {
                throw new BadRequestException("Ghế đã được đặt hoặc không tìm thấy thông tin đặt ghế với seatId: " + ticketRequest.seatId + " và showtimeId: " + ticketRequest.showtimeId);
            }

            ShowtimeEntity showTime = getShowtimeDetails(ticketRequest.showtimeId);
            if (showTime == null) {
                throw new BadRequestException("Không tìm thấy buổi chiếu với showtimeId: " + ticketRequest.showtimeId);
            }
            MovieEntity movies = getMovieDetail(showTime.movies_id);
            if (movies == null) {
                throw new BadRequestException("Không tìm thấy thông tin phim cho buổi chiếu với showtimeId: " + ticketRequest.showtimeId);
            }

            totalPrice += movies.getPrice(); // Chỉ gọi getPrice nếu movies không null

            TicketEntity ticketEntity = bookSingleTicket(ticketRequest, user, billEntity);
            bookedTickets.add(ticketEntity);
        }

        // Lưu hóa đơn vào cơ sở dữ liệu
        billEntity.setTotal_amount(totalPrice);
        billEntity.setMember_id(user);
        billEntity.setStatus(true);
        mysql.insertOne(CinemaSql.saveBill(billEntity.paymentTime, billEntity.total_amount, billEntity.member_id.id, billEntity.status));

        return bookedTickets;
    }

    private TicketEntity bookSingleTicket(BookTicketRequest ticketRequest, UserEntity user, BillEntity billEntity) throws Exception {
        // Cập nhật trạng thái đặt ghế
        mysql.updateOne(CinemaSql.bookSeat(ticketRequest.showtimeId, ticketRequest.seatId));

        // Kiểm tra số lượng vé đã đặt
        long bookedTicketsCount = mysql.count(CinemaSql.countByShowtimeId(ticketRequest.showtimeId));
        SeatBookingEntity seatBooking = mysql.selectOne(
            CinemaSql.findBySeatIdAndShowtimeId(ticketRequest.seatId, ticketRequest.showtimeId),
            SeatBookingEntity.class
        );

        if (seatBooking == null) {
            throw new BadRequestException("Không tìm thấy thông tin đặt ghế với seatId: " + ticketRequest.seatId + " và showtimeId: " + ticketRequest.showtimeId);
        }

        ShowtimeEntity showTime = getShowtimeDetails(ticketRequest.showtimeId);
        if (showTime == null) {
            throw new BadRequestException("Không tìm thấy thông tin lịch chiếu cho ID: " + ticketRequest.showtimeId);
        }

        // Kiểm tra nếu số lượng vé đã đặt lớn hơn hoặc bằng sức chứa của phòng
        RoomEntity room = showTime.getRoom();
        if (room == null) {
            // Nếu không có thông tin phòng trong showTime, gọi getRoomDetail() để lấy thông tin
            room = getRoomDetail(showTime.room_id);
            if (room == null) {
                throw new BadRequestException("Không tìm thấy thông tin phòng cho buổi chiếu với showtimeId: " + ticketRequest.showtimeId);
            }
        }
        if (bookedTicketsCount >= room.getCapacity()) {
            throw new BadRequestException("Lịch chiếu đã hết chỗ trống.");
        }
        MovieEntity movies = getMovieDetail(showTime.movies_id);
        if (movies == null) {
            throw new BadRequestException("Không tìm thấy thông tin phim cho buổi chiếu với showtimeId: " + ticketRequest.showtimeId);
        }
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setPrice(movies.price);
        ticketEntity.setBill_id(billEntity);
        ticketEntity.setSeat_booking(seatBooking);

        // Lưu ticketEntity vào cơ sở dữ liệu
        mysql.insertOne(CinemaSql.saveTicket(ticketEntity.price, ticketEntity.bill_id.id, ticketEntity.seat_booking.id));

        return ticketEntity;
    }

    public SeatBookingEntity findBySeatIdAndShowtimeId(BookTicketRequest request) throws Exception {
        // Tìm kiếm thông tin đặt chỗ ghế
        SeatBookingEntity seatBooking = mysql.selectOne(
            CinemaSql.findBySeatIdAndShowtimeId(request.seatId, request.showtimeId),
            SeatBookingEntity.class
        );

        // Kiểm tra nếu không tìm thấy thông tin đặt chỗ ghế
        if (seatBooking == null) {
            throw new BadRequestException("Không tìm thấy thông tin đặt ghế với seatId: " + request.seatId + " và showtimeId: " + request.showtimeId);
        }

        // Lấy chi tiết suất chiếu
        ShowtimeEntity showtime = getShowtimeDetails(request.showtimeId);
        seatBooking.setShowtime(showtime);  // Gán thông tin suất chiếu vào seatBooking

        // Gán thông tin ghế vào seatBooking nếu cần (giả định seat_id đã được gán)
        SeatEntity seat = getSeatDetail(seatBooking.getSeat_id());
        seatBooking.setSeat(seat);  // Gán thông tin ghế vào seatBooking

        return seatBooking;
    }

    public TicketEntity details(Integer id) throws Exception {
        TicketEntity ticket = mysql.selectOne(CinemaSql.selectTicket(id), TicketEntity.class);
        if (ticket == null) {
            throw new Exception("Ticket not found with ID: " + id);
        }
        if (ticket.getSeat_booking_id() != null) {
            SeatBookingEntity seatBooking = getSeatbookingDetail(ticket.getSeat_booking_id());
            if (seatBooking.getShowtime_id() != null) {
                ShowtimeEntity showtime = getShowtimeDetails(seatBooking.getShowtime_id());
                seatBooking.setShowtime(showtime);
            }
            if (seatBooking.getSeat_id() != null) {
                SeatEntity seat = getSeatDetail(seatBooking.getSeat_id());
                seatBooking.setSeat(seat);
            }
            ticket.setSeat_booking(seatBooking);
        } else {
            System.out.println("No showtime and seat found for seatbokiing.");
        }
        return ticket;
    }

    public ShowtimeEntity getShowtimeDetails(Integer id) {
        return mysql.selectOne(CinemaSql.selectDetailShowtime(id), ShowtimeEntity.class);
    }

    public SeatEntity getSeatDetail(Integer id) {
        return mysql.selectOne(CinemaSql.selectSeatById(id), SeatEntity.class);
    }

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

    public SeatBookingEntity getSeatbookingDetail(Integer id) {
        return mysql.selectOne(CinemaSql.selectSeatBooking(id), SeatBookingEntity.class);
    }

    public BillEntity getBillDetail(Integer id) {
        return mysql.selectOne(CinemaSql.selectBill(id), BillEntity.class);
    }

}
