package com.oie.cms.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {
    private final String secret;

    public JwtService(Environment env) {
        this.secret = env.getProperty("JWT_SECRET");
    }

    public String generateToken(AuthUser user) {
        var payload = new HashMap<String, Object>();
        payload.put("name", user.getEmployee().getName());
        payload.put("role", user.getEmployee().getRole());

        var jwtExpiryDuration = 24 * 60 * 60 * 1000; // 1 day
        return Jwts.builder()
                .setClaims(payload)
                .setSubject(user.getEmployee().getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiryDuration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String validateTokenAndGetSubject(String token) throws JwtException {
        var jwt = Jwts.parser().setSigningKey(secret).parse(token);
        var payload = (Claims) jwt.getBody();
        return payload.getSubject();
    }
}
