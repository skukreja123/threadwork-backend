package com.threadwork.controller;
import com.threadwork.dto.ProductDtos.*;
import com.threadwork.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class ProductController {
  private final ProductService service;
  @GetMapping("/products") public List<ProductView> shop() { return service.listActive(); }
  @GetMapping("/admin/products") public List<ProductView> all() { return service.listAll(); }
  @PostMapping("/admin/products") public ProductView create(@Valid @RequestBody ProductRequest r) { return service.create(r); }
  @PutMapping("/admin/products/{id}") public ProductView update(@PathVariable UUID id, @Valid @RequestBody ProductRequest r) { return service.update(id, r); }
  @DeleteMapping("/admin/products/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable UUID id) { service.delete(id); }
}
