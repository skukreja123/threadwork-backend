package com.threadwork.service;
import com.threadwork.dto.AuthDtos.*;
import com.threadwork.exception.ApiException;
import com.threadwork.model.User;
import com.threadwork.repository.UserRepository;
import com.threadwork.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
@Service @RequiredArgsConstructor
public class AuthService {
  private final UserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;

  @Transactional public AuthResponse signup(SignupRequest r) {
    String email = r.email().trim().toLowerCase();
    if (users.existsByEmail(email)) throw new ApiException(HttpStatus.CONFLICT, "That email is already registered");
    User u = new User();   // always a customer here; only an admin can promote someone
    u.setName(r.name().trim()); u.setEmail(email); u.setPasswordHash(encoder.encode(r.password()));
    users.save(u);
    return new AuthResponse(jwt.create(u), UserView.from(u));
  }
  @Transactional(readOnly = true) public AuthResponse login(LoginRequest r) {
    User u = users.findByEmail(r.email().trim().toLowerCase()).filter(x -> encoder.matches(r.password(), x.getPasswordHash()))
      .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Wrong email or password"));
    return new AuthResponse(jwt.create(u), UserView.from(u));
  }
  @Transactional(readOnly = true) public UserView me(UUID id) {
    return users.findById(id).map(UserView::from).orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Log in first"));
  }
}
