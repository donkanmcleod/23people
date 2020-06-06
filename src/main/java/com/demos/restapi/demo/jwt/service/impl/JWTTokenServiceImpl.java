/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demos.restapi.demo.jwt.service.impl;

import com.demos.restapi.demo.jwt.model.User;
import com.demos.restapi.demo.jwt.model.UserPrincipal;
import com.demos.restapi.demo.jwt.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.stereotype.Component;


/**
 *
 * @author marcello
 */
@Component("JWTTokenService")
public class JWTTokenServiceImpl implements TokenService {
    
    private String JWT_SECRET = "1234567890123456789012345678901234567890";
    
    
    @Override
    public String generateToken() {

        User u = new User(0, "no_one", "no_one", true);


        return this.generateToken(u);
    }
    

    @Override
    public String generateToken(User user) {
        
        Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
        Date expirationDate = Date.from(expirationTime);

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

        String compactTokenString = Jwts.builder()
                .claim("id", user.getId())
                .claim("sub", user.getUsername())
                .claim("admin", user.isAdmin())
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();


        return "Bearer " + compactTokenString;
    }
    
    
    @Override
    public UserPrincipal parseToken(String token) {
        
        try {
                byte[] secretBytes = JWT_SECRET.getBytes();

                Jws<Claims> jwsClaims = Jwts.parserBuilder()
                        .setSigningKey(secretBytes)
                        .build()
                        .parseClaimsJws(token);

                String username = jwsClaims.getBody().getSubject();
                Integer userId = jwsClaims.getBody().get("id", Integer.class);
                boolean isAdmin = jwsClaims.getBody().get("admin", Boolean.class);


                return new UserPrincipal(userId, username, isAdmin);
        
        } catch (Exception e) {
        
            return new UserPrincipal(0, "", false);
        }
    }    

}
