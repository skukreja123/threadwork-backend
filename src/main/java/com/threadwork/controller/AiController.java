package com.threadwork.controller;
import com.threadwork.dto.AiDtos.*;
import com.threadwork.security.AuthUser;
import com.threadwork.service.AiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/ai") @RequiredArgsConstructor
public class AiController {
  private final AiService service;
  @PostMapping("/generate") public AiResult generate(@AuthenticationPrincipal AuthUser u, @Valid @RequestBody AiRequest r) { return service.generate(u.id(), r.prompt()); }
}
