package com.DATN.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    // Mật khẩu bí mật của Server (Bắt buộc phải dài trên 32 ký tự)
    private final String SECRET_STRING = "MotDoanMaBiMatCuaHeThongQuanLyNhanSuRatDaiVaAnToan123456";
    private final long EXPIRATION_TIME = 86400000; // 24 giờ

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_STRING.getBytes());
    }

    // 1. In thẻ JWT
    public String generateToken(Integer accountId, String username, String role) {
        return Jwts.builder() 
                .subject(username)
                .claim("accountId", accountId)
                .claim("role", role) 
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignKey())
                .compact();
    }

    // 2. Đọc thẻ JWT để lấy Username
    public String extractUsername(String token) {
        return Jwts.parser() 
                .verifyWith(getSignKey()) 
                .build() 
                .parseSignedClaims(token) 
                .getPayload() 
                .getSubject(); 
    }
    
    public String extractRole(String token) {
        return Jwts.parser() 
                .verifyWith(getSignKey()) 
                .build() 
                .parseSignedClaims(token) 
                .getPayload() 
                .get("role", String.class);
    }
    
    public Integer extractAccountId(String token) {
    return Jwts.parser()
            .verifyWith(getSignKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("accountId", Integer.class);
}

    // 3. Kiểm tra thẻ còn hạn không
    public boolean isTokenValid(String token) {
        try {
            extractUsername(token);
            return true;
        } catch (Exception e) {
            return false; // Thẻ giả mạo hoặc đã hết hạn
        }
    }
}
