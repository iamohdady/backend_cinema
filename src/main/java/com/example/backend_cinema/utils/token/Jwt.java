package com.example.backend_cinema.utils.token;

import com.example.backend_cinema.utils.model.Pair;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Strings;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

public class Jwt {

    private static final String SUBJECT = "pharmacy-token";
    private static final String USERNAME_KEY = "pharmacy-username";
    private static final String ROLE_KEY = "pharmacy_role";
    private static final long TOKEN_EXPIRE_TIME = 60 * 60 * 1000;
    private static final String SECRET_KEY = "yoByMC7MWgXDZ5EraPtQi6z22LUcoHQjp5MnY9uOaoc=";
    private static final String SIGNATURE_ALGORITHM = "HmacSHA256";

    public static String generateToken(String username, String role) {
        long currentTimeInMillis = System.currentTimeMillis();

        return Jwts.builder()
            .setSubject(SUBJECT)
            .claim(USERNAME_KEY, username)
            .claim(ROLE_KEY, role)
            .setExpiration(new Date((currentTimeInMillis + TOKEN_EXPIRE_TIME)))
            .signWith(new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), SIGNATURE_ALGORITHM))
            .compact();
    }

    public static Pair<String, String> decodeToken(String token) {
        if (Strings.hasLength(token)) {
            Claims claims = decodeJWT(token);

            if (claims != null) {
                Date expiration = claims.getExpiration();
                if (expiration != null && expiration.after(new Date())) {
                    String username = String.valueOf(claims.get(USERNAME_KEY));
                    String role = String.valueOf(claims.get(ROLE_KEY));
                    return new Pair<>(username, role);
                }
            }
        }
        return null;
    }

    private static Claims decodeJWT(String jwt) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), SIGNATURE_ALGORITHM))
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}