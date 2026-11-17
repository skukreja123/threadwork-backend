package com.threadwork.controller;
import com.threadwork.dto.AuthDtos.*;
import com.threadwork.security.AuthUser;
import com.threadwork.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth") @RequiredArgsConstructor
public class AuthController {
  private final AuthService service;
  @PostMapping("/signup") public AuthResponse signup(@Valid @RequestBody SignupRequest r) { return service.signup(r); }
  @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest r) { return service.login(r); }
  @GetMapping("/me") public UserView me(@AuthenticationPrincipal AuthUser u) { return service.me(u.id()); }
}
