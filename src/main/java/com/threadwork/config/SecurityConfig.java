package com.threadwork.config;
import com.threadwork.security.JwtAuthFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.io.IOException;
import java.util.List;
/** The route access table: who may call which URL. Read this file to see every rule in one place. */
@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig {
  private final JwtAuthFilter jwtFilter;
  @Value("${app.cors-origin}") private String corsOrigin;

  @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(c -> c.disable()).cors(Customizer.withDefaults())
      .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(a -> a
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .requestMatchers("/api/auth/login", "/api/auth/signup").permitAll()   // public
        .requestMatchers(HttpMethod.GET, "/api/products").permitAll()         // public shop
        .requestMatchers(HttpMethod.POST, "/api/messages").permitAll()        // contact form
        .requestMatchers("/api/admin/orders/**").hasAnyRole("STAFF", "ADMIN") // staff and admin
        .requestMatchers("/api/admin/**").hasRole("ADMIN")                    // admin only
        .anyRequest().authenticated())                                         // everything else: any logged-in user
      .exceptionHandling(e -> e
        .authenticationEntryPoint((rq, rs, ex) -> json(rs, 401, "Log in first"))
        .accessDeniedHandler((rq, rs, ex) -> json(rs, 403, "Not allowed")))
      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
  private static void json(HttpServletResponse rs, int code, String msg) throws IOException {
    rs.setStatus(code); rs.setContentType("application/json"); rs.getWriter().write("{\"error\":\"" + msg + "\"}");
  }
  @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(12); }
  @Bean CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration c = new CorsConfiguration();
    c.setAllowedOrigins(List.of(corsOrigin)); c.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); c.setAllowedHeaders(List.of("*"));
    UrlBasedCorsConfigurationSource s = new UrlBasedCorsConfigurationSource(); s.registerCorsConfiguration("/**", c); return s;
  }
}
