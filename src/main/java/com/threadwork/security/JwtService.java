package com.threadwork.security;
import com.threadwork.model.Role;
import com.threadwork.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
@Service
public class JwtService {
  private final SecretKey key;
  public JwtService(@Value("${app.jwt-secret}") String secret) { this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); }
  public String create(User u) {
    return Jwts.builder().subject(u.getId().toString()).claim("role", u.getRole().name()).claim("name", u.getName())
      .issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + 7L * 24 * 3600 * 1000)).signWith(key).compact();
  }
  public AuthUser parse(String token) {
    Claims c = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    return new AuthUser(UUID.fromString(c.getSubject()), Role.valueOf(c.get("role", String.class)), c.get("name", String.class));
  }
}
