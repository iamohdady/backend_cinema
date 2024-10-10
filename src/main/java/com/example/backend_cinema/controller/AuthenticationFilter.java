package com.example.backend_cinema.controller;

import com.example.backend_cinema.mysql.MySqlConnector;
import com.example.backend_cinema.utils.model.Pair;
import com.example.backend_cinema.utils.token.Jwt;
import io.jsonwebtoken.lang.Strings;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> READ_ROLE = new ArrayList<>();
    private static final List<String> WRITE_ROLE = new ArrayList<>();
    private static final List<String> ADMIN_ROLE = new ArrayList<>();

    static {
        READ_ROLE.add("/dashboard/user/list");
        READ_ROLE.add("/dashboard/user/add");
        READ_ROLE.add("/dashboard/user/update_password");
        READ_ROLE.add("/dashboard/user/update_role");
        READ_ROLE.add("/dashboard/user/forget_password");
        READ_ROLE.add("/dashboard/user/detail");
        READ_ROLE.add("/dashboard/user/update_user");
        READ_ROLE.add("/dashboard/user/delete");
        READ_ROLE.add("/dashboard/user/count");

        READ_ROLE.add("/dashboard/movie/add");
        READ_ROLE.add("/dashboard/movie/list");
        READ_ROLE.add("/dashboard/movie/detail");
        READ_ROLE.add("/dashboard/movie/detail/id");

        READ_ROLE.add("/dashboard/movie/update_movie");
        READ_ROLE.add("/dashboard/movie/delete");
        READ_ROLE.add("/dashboard/movie/count");
        READ_ROLE.add("/dashboard/movie/search");
        READ_ROLE.add("/dashboard/movie/upcoming");

        READ_ROLE.add("/dashboard/room/add");
        READ_ROLE.add("/dashboard/room/list");
        READ_ROLE.add("/dashboard/room/detail");
        READ_ROLE.add("/dashboard/room/seats");
        READ_ROLE.add("/dashboard/room/update_status");
        READ_ROLE.add("/dashboard/room/available_room/daytime");

        READ_ROLE.add("/dashboard/daytime/add");
        READ_ROLE.add("/dashboard/daytime/list");
        READ_ROLE.add("/dashboard/daytime/update_daytime");
        READ_ROLE.add("/dashboard/daytime/delete");

        READ_ROLE.add("/dashboard/schedule/list");
        READ_ROLE.add("/dashboard/schedule/addByDaytimeName");
        READ_ROLE.add("/dashboard/schedule/updateByDaytimeName");

        READ_ROLE.add("/dashboard/showtime/list");
        READ_ROLE.add("/dashboard/showtime/add");
        READ_ROLE.add("/dashboard/showtime/detail");
        READ_ROLE.add("/dashboard/showtime/calculate/endtime");

        READ_ROLE.add("/dashboard/ticket/book_tickets");
        READ_ROLE.add("/dashboard/ticket/get");
        READ_ROLE.add("/dashboard/ticket/detail");

        WRITE_ROLE.addAll(READ_ROLE);

        ADMIN_ROLE.addAll(WRITE_ROLE);
    }

    private final MySqlConnector mysql;

    @Autowired
    public AuthenticationFilter(MySqlConnector mysql) {
        this.mysql = mysql;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        if (shouldByPass(request)) {
            filterChain.doFilter(request, response);
        } else {
            Pair<String, String> userInformation = Jwt.decodeToken(request.getHeader(HttpHeaders.AUTHORIZATION));
            if (isValidCredentials(userInformation)) {
                String username = userInformation.key;
                String role = userInformation.value;

//                isUserActive(username) &&
                if (hasPermission(request, role)) {
                    request.setAttribute("fromUser", username);
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }

//    private boolean isUserActive(String username){
//        String status = getStatusByUsername(username);
//        return "ACTIVE".equals(status) || "INVITED".equals(status);
//    }

    private boolean shouldByPass(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.contains("/dashboard/user/login")
//            || uri.contains("/dashboard/user/refresh_token")
//            || uri.contains("/dashboard/user/update_password_first_time")
            || !"POST".equalsIgnoreCase(request.getMethod());
    }

    private boolean isValidCredentials(Pair<String, String> userInformation) {
        return userInformation != null &&
            Strings.hasLength(userInformation.key) &&
            Strings.hasLength(userInformation.value);
    }

    private boolean hasPermission(HttpServletRequest request, String role) {
        String uri = request.getRequestURI();
        if ("ADMIN".equals(role)) {
            return ADMIN_ROLE.stream().anyMatch(e -> e.contains(uri));
        } else if ("WRITE".equals(role)) {
            return WRITE_ROLE.stream().anyMatch(e -> e.contains(uri));
        } else
            return READ_ROLE.stream().anyMatch(e -> e.contains(uri));
    }

//    private String getStatusByUsername(String username) {
//        UserEntity userEntity = mysql.selectOne(PharmacySql.selectStatus(username), UserEntity.class);
//
//        if (userEntity != null) {
//            return userEntity.status;
//        } else {
//            return null;
//        }
//    }
}
