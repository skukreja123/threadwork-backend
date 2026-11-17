package com.threadwork.controller;
import com.threadwork.dto.UserDtos.*;
import com.threadwork.security.AuthUser;
import com.threadwork.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
@RestController @RequestMapping("/api/admin/users") @RequiredArgsConstructor
public class UserController {
  private final UserService service;
  @GetMapping public List<AdminUserView> list() { return service.list(); }
  @PatchMapping("/{id}/role") public AdminUserView role(@AuthenticationPrincipal AuthUser me, @PathVariable UUID id, @Valid @RequestBody RoleRequest r) { return service.changeRole(me.id(), id, r.role()); }
}
