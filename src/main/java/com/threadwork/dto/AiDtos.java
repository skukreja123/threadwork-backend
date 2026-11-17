package com.threadwork.dto;
import jakarta.validation.constraints.NotBlank;
public final class AiDtos {
  private AiDtos() {}
  public record AiRequest(@NotBlank String prompt) {}
  /** imageUrl stays null until a real image provider is connected; remaining is null on unlimited plans. */
  public record AiResult(String imageUrl, Integer remaining) {}
}
