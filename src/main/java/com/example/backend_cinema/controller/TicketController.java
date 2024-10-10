package com.example.backend_cinema.controller;


import com.example.backend_cinema.mysql.entity.SeatBookingEntity;
import com.example.backend_cinema.mysql.entity.TicketEntity;
import com.example.backend_cinema.mysql.entity.UserEntity;
import com.example.backend_cinema.request.BookTicketRequest;
import com.example.backend_cinema.request.TicketRequest;
import com.example.backend_cinema.request.UsernameRequest;
import com.example.backend_cinema.service.RoomService;
import com.example.backend_cinema.service.TicketService;
import com.example.backend_cinema.utils.model.CinemaResponse;
import com.example.backend_cinema.utils.model.Pair;
import com.example.backend_cinema.utils.token.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard/ticket")
public class TicketController {

    private final TicketService service;

    @Autowired
    public TicketController(TicketService service) {
        this.service = service;
    }

    @PostMapping("/book_tickets")
    public ResponseEntity<CinemaResponse<List<TicketEntity>>> bookTickets(@RequestBody List<BookTicketRequest> requests, HttpServletRequest httpServletRequest) throws Exception {
        // Lấy username từ token
        Pair<String, String> userInformation = Jwt.decodeToken(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
        if (userInformation == null || Strings.isEmpty(userInformation.key)) {
            return new ResponseEntity<>(new CinemaResponse<>(null), HttpStatus.UNAUTHORIZED);
        }

        String username = userInformation.key; // Lấy username từ token

        // Gọi phương thức bookTickets trong service với username đã lấy
        List<TicketEntity> bookedTickets = service.bookTickets(requests, username);

        return new ResponseEntity<>(new CinemaResponse<>(bookedTickets), HttpStatus.OK);
    }

    @PostMapping("/get")
    public ResponseEntity<CinemaResponse<SeatBookingEntity>> getMemberDetails(@RequestBody BookTicketRequest request) throws Exception {
        SeatBookingEntity member = service.findBySeatIdAndShowtimeId(request);
        return new ResponseEntity<>(
            new CinemaResponse<>(member),
            HttpStatus.OK
        );
    }

    @PostMapping("/detail")
    public ResponseEntity<CinemaResponse<TicketEntity>> details(@RequestBody TicketRequest request) throws Exception {
        TicketEntity ticket = service.details(request.id);
        return new ResponseEntity<>(
            new CinemaResponse<>(ticket),
            HttpStatus.OK
        );
    }
}
