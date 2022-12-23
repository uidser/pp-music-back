package com.uidser.ppmusic.common.jjwt;

import com.uidser.ppmusic.common.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JJwtUtil {

    private static final String key = "25f9e794323b453885f5181f1b624d0b";
    private static final Long time = 60L * 1000L * 24L * 30L;

    public static String createJwtToken(User umsMemberEntity) {
        Long now = System.currentTimeMillis();
        long exp = now + time;
        JwtBuilder jwtBuilder = Jwts.builder()
                .setExpiration(new Date(exp))
                .setHeaderParam("createTime", new Date())
                .claim("username", umsMemberEntity.getUsername())
                .signWith(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256);
        return jwtBuilder.compact();
    }

    public static String createJwtToken(String username) {
        Long now = System.currentTimeMillis();
        long exp = now + time;
        JwtBuilder jwtBuilder = Jwts.builder()
                .setExpiration(new Date(exp))
                .setHeaderParam("createTime", new Date())
                .claim("username", username)
                .signWith(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256);
        return jwtBuilder.compact();
    }

    public static String getUserByToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        if(token == null) {
            throw new RuntimeException("token为空");
        }
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token);
        Object username = claimsJws.getBody().get("username");
        return username.toString();
    }

    public static String getUserByToken(String token) {
        if(token == null) {
            throw new RuntimeException("token为空");
        }
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token);
        Object username = claimsJws.getBody().get("username");
        return username.toString();
    }

    public static String getJws(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        if(token == null) {
            throw new RuntimeException("token为空");
        }
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token);
        Object username = claimsJws.getBody().get("username");
        return username.toString();
    }

    public static Jws<Claims> getJws(String jwtToken) {
        if(jwtToken == null) {
            throw new RuntimeException("token为空");
        }
        return Jwts.parserBuilder().setSigningKey(key.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(jwtToken);
    }

}
