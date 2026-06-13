package com.DATN.security.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.DATN.security.util.JwtUtil;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final JwtUtil jwtUtil; 

    @Override 
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException { 
        
        // 1. Tìm thẻ JWT trong Header của gói tin mạng (có tên là "Authorization")
        String authHeader = request.getHeader("Authorization"); 
        String token = null;
        String username = null;

        // Thẻ JWT chuẩn luôn bắt đầu bằng chữ "Bearer " (người mang vé)
        if (authHeader != null && authHeader.startsWith("Bearer ")) { 
            token = authHeader.substring(7); // Cắt bỏ 7 ký tự "Bearer " để lấy đúng cái mã
            try {
                username = jwtUtil.extractUsername(token); // Giải mã lấy username
            }catch (Exception e) { 
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                response.setContentType(
                        "application/json");

                response.setCharacterEncoding(
                        "UTF-8");

                response.getWriter().write(
                        "{\"message\":\"Token không hợp lệ hoặc đã hết hạn\"}");

                return;
            }
        }

        // 2. Nếu thẻ hợp lệ và hệ thống chưa ghi nhận người này
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { 
            if (jwtUtil.isTokenValid(token)) {  
                String role = jwtUtil.extractRole(token);
                java.util.List<SimpleGrantedAuthority> authorities = 
                    java.util.Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));

                // Đóng dấu "Đã xác thực" và cho phép đi qua cổng
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(  
                        username, null, authorities
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 3. Mở cổng cho gói tin đi tiếp vào Controller
        filterChain.doFilter(request, response);
    }
}
