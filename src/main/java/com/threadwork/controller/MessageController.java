package com.threadwork.controller;
import com.threadwork.dto.MessageDtos.*;
import com.threadwork.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class MessageController {
  private final MessageService service;
  @PostMapping("/messages") public Map<String, Boolean> send(@Valid @RequestBody MessageRequest r) { service.create(r); return Map.of("ok", true); }
  @GetMapping("/admin/messages") public List<MessageView> list() { return service.list(); }
}
