package com.threadwork.config;
import com.threadwork.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/** Serves the uploaded photos at /uploads/**. */
@Configuration @RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
  private final FileStorageService storage;
  @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/uploads/**").addResourceLocations(storage.dir().toUri().toString()).setCachePeriod(3600);
  }
}
