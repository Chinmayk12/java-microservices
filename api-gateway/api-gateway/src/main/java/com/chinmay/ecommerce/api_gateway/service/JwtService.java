package com.chinmay.ecommerce.api_gateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    public String JwtSecretKey;

    private SecretKey getSecretKey() {
        //Converts your string secret key into a SecretKey object using HMAC-SHA.
        return Keys.hmacShaKeyFor(JwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Long getUserIdFromToken(String token)
    {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());   // Will return the user id from the token generated
    }
}
