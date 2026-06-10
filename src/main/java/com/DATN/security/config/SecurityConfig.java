package com.DATN.security.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.DATN.security.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity 
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter; 

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // dịch mật khẩu
    }

    @Bean 
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { 
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            
            //Báo cho Spring biết ta dùng JWT (phiên làm việc không lưu trạng thái)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
            
            .authorizeHttpRequests(auth -> auth 
                
                .requestMatchers("/api/auth/login").permitAll() 
                .anyRequest().authenticated()              
            )
            
            
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); 

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
