package com.threadwork.controller;
import com.threadwork.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;
@RestController @RequestMapping("/api/admin/uploads") @RequiredArgsConstructor
public class UploadController {
  private final FileStorageService storage;
  @PostMapping public Map<String, String> upload(@RequestParam("file") MultipartFile file) { return Map.of("url", storage.save(file)); }
}
