package com.threadwork.controller;
import com.threadwork.dto.OrderDtos.*;
import com.threadwork.security.AuthUser;
import com.threadwork.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class OrderController {
  private final OrderService service;
  @PostMapping("/orders") public CreateOrderResponse create(@AuthenticationPrincipal AuthUser u, @Valid @RequestBody CreateOrderRequest r) { return service.create(u.id(), r); }
  @GetMapping("/orders/mine") public List<OrderView> mine(@AuthenticationPrincipal AuthUser u) { return service.mine(u.id()); }
  @GetMapping("/admin/orders") public List<OrderView> all() { return service.all(); }
  @PatchMapping("/admin/orders/{id}/status") public OrderView status(@PathVariable UUID id, @Valid @RequestBody StatusRequest r) { return service.updateStatus(id, r.status()); }
}
